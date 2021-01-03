package com.shawn.jooo.framework.mybatis.plugin;

import com.shawn.jooo.framework.mybatis.reflect.BeanReflections;
import org.apache.commons.lang3.ClassUtils;
import org.apache.ibatis.executor.Executor;
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

import javax.persistence.Transient;
import java.lang.reflect.Field;
import java.util.*;


/**
 * Mybatis结果映射插件
 *
 * @author shawn
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class MybatisResultMapPlugin implements Interceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MybatisResultMapPlugin.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        if (!(invocation.getTarget() instanceof Executor)) {
            return invocation.proceed();
        }
        MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
        //xml sql 不做处理
        if (ms.getResource().contains(".xml")) {
            return invocation.proceed();
        }

        //已添加映射，不做处理
        ResultMap resultMap = ms.getResultMaps().iterator().next();
        if (resultMap.getResultMappings() != null && resultMap.getResultMappings().size() > 0) {
            return invocation.proceed();
        }

        Class<?> entityType = resultMap.getType();
        if (ClassUtils.isAssignable(entityType, Collection.class)) {
            return invocation.proceed();
        }

        List<ResultMapping> resultMappings = new ArrayList<>(8);
        while (Object.class != entityType && entityType != null) {
            for (Field field : entityType.getDeclaredFields()) {
                if (field.isAnnotationPresent(Transient.class)) {
                    continue;
                }
                String columnName = BeanReflections.getColumnName(field);
                if (columnName != null) {
                    String fieldName = BeanReflections.getFiledName(field);
                    Class<?> filedType = BeanReflections.getFieldType(field);
                    JdbcType jdbcType = BeanReflections.getJdbcType(field);
                    ResultMapping resultMapping = new ResultMapping.Builder(ms.getConfiguration(), fieldName)
                            .column(columnName).javaType(filedType).jdbcType(jdbcType).build();
                    resultMappings.add(resultMapping);
                }
            }
            entityType = entityType.getSuperclass();
        }

        ResultMap newResultMap = new ResultMap.Builder(ms.getConfiguration(), resultMap.getId(), resultMap.getType(), resultMappings).build();
        Field field = ReflectionUtils.findField(MappedStatement.class, "resultMaps");
        ReflectionUtils.makeAccessible(field);
        ReflectionUtils.setField(field, ms, Collections.singletonList(newResultMap));
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
