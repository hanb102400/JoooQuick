package com.shawn.jooo.framework.mybatis.plugin;

import com.shawn.jooo.framework.mybatis.reflect.BeanReflections;
import org.apache.commons.lang3.ClassUtils;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.JdbcType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;


/**
 * Mybatis结果映射插件
 *
 * @author shawn
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class MybatisResultMapPlugin implements Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(MybatisResultMapPlugin.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if("query".equals(invocation.getMethod().getName())) {
            return interceptResultMap(invocation);
        }
        if("update".equals(invocation.getMethod().getName())) {
            return interceptReturnKey(invocation);
        }
        return invocation.proceed();
    }


    /**
     * 自动生成resultMap字段配置
     *
     * @param invocation
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private Object interceptResultMap(Invocation invocation) throws InvocationTargetException, IllegalAccessException {

        Object[] args = invocation.getArgs();
        MappedStatement mappedStatement = (MappedStatement) args[0];

        //xml sql 不做处理
        if (mappedStatement.getResource().contains(".xml")) {
            return invocation.proceed();
        }

        //已添加映射，不做处理
        ResultMap resultMap = mappedStatement.getResultMaps().iterator().next();
        if (resultMap.getResultMappings() != null && resultMap.getResultMappings().size() > 0) {
            return invocation.proceed();
        }

        Class<?> entityType = resultMap.getType();
        if (ClassUtils.isAssignable(entityType, Collection.class)) {
            return invocation.proceed();
        }

        List<ResultMapping> resultMappings = new ArrayList<>(8);
        List<Field> fields = BeanReflections.getFields(entityType);

        for (Field field : fields) {
            if (BeanReflections.isColumn(field)) {
                String columnName = BeanReflections.getColumnName(field);
                if (columnName != null) {
                    String fieldName = BeanReflections.getFieldName(field);
                    Class<?> filedType = BeanReflections.getFieldType(field);
                    JdbcType jdbcType = BeanReflections.getColumnJdbcType(field);
                    ResultMapping resultMapping = new ResultMapping.Builder(mappedStatement.getConfiguration(), fieldName)
                            .column(columnName).javaType(filedType).jdbcType(jdbcType).build();
                    resultMappings.add(resultMapping);
                }
            }
        }

        //动态添加ResultMap映射
        ResultMap newResultMap = new ResultMap.Builder(mappedStatement.getConfiguration(), resultMap.getId(), resultMap.getType(), resultMappings).build();
        Field field = ReflectionUtils.findField(MappedStatement.class, "resultMaps");
        ReflectionUtils.makeAccessible(field);
        ReflectionUtils.setField(field, mappedStatement, Collections.singletonList(newResultMap));

        return invocation.proceed();
    }

    /**
     * 生成主键，并插入到entity中
     *
     * @param invocation
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private Object interceptReturnKey(Invocation invocation) throws InvocationTargetException, IllegalAccessException {
        Object[] args = invocation.getArgs();

        MappedStatement mappedStatement = (MappedStatement) args[0];
        Object object = args[1];
        Class<?> entityType = object.getClass();

        //xml sql 不做处理
        if (mappedStatement.getResource().contains(".xml")) {
            return invocation.proceed();
        }
        //已添加映射，不做处理
        ResultMap resultMap = mappedStatement.getResultMaps().iterator().next();
        if (resultMap.getResultMappings() != null && resultMap.getResultMappings().size() > 0) {
            return invocation.proceed();
        }

        if (ClassUtils.isAssignable(entityType, Collection.class)) {
            return invocation.proceed();
        }

        mappedStatement.getConfiguration().setUseGeneratedKeys(true);

        List<Field> fields = BeanReflections.getFields(entityType);
        List<String> keyProperties = new ArrayList<>();
        List<String> keyColumns = new ArrayList<>();

        for (Field field : fields) {
            if (BeanReflections.isId(field)) {
                String fieldName = BeanReflections.getFieldName(field);
                String columnName = BeanReflections.getColumnName(field);
                keyProperties.add(fieldName);
                keyColumns.add(columnName);
            }
        }

        if (keyProperties.size() > 0 && keyColumns.size() > 0) {
            //动态添加主键生成器
            Field field = ReflectionUtils.findField(MappedStatement.class, "keyGenerator");
            ReflectionUtils.makeAccessible(field);
            ReflectionUtils.setField(field, mappedStatement, new Jdbc3KeyGenerator());
            //主键属性和列
            field = ReflectionUtils.findField(MappedStatement.class, "keyProperties");
            ReflectionUtils.makeAccessible(field);
            ReflectionUtils.setField(field, mappedStatement, keyProperties.toArray(new String[keyProperties.size()]));
            field = ReflectionUtils.findField(MappedStatement.class, "keyColumns");
            ReflectionUtils.makeAccessible(field);
            ReflectionUtils.setField(field, mappedStatement, keyColumns.toArray(new String[keyColumns.size()]));
        }

        return invocation.proceed();
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
