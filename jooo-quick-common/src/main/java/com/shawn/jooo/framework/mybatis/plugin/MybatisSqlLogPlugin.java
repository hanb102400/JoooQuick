package com.shawn.jooo.framework.mybatis.plugin;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * MybatisSQL日志插件
 * <p>
 * 默认关闭，开启需要在插件设置 show_log：true
 *
 * @author shawn
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})
})
public class MybatisSqlLogPlugin implements Interceptor {

    private boolean showSql = false;

    private static final Logger LOGGER = LoggerFactory.getLogger(MybatisSqlLogPlugin.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        Object target = invocation.getTarget();
        String method = invocation.getMethod().getName();

        if (!showSql || target == null) {
            return invocation.proceed();
        } else if (target instanceof Executor) {
            MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
            Object parameter = null;
            Object returnValue;
            if (invocation.getArgs().length > 1) {
                parameter = invocation.getArgs()[1];
            }
            String sqlId = mappedStatement.getId();
            BoundSql boundSql = mappedStatement.getBoundSql(parameter);
            Configuration configuration = mappedStatement.getConfiguration();
            String sql = getSql(configuration, boundSql, sqlId);
            LOGGER.debug("<==             Sql: " + sql);
            long start = System.currentTimeMillis();
            try {
                returnValue = invocation.proceed();
            } catch (Exception e) {
                sql = getSql(configuration, boundSql, sqlId);
                LOGGER.error("[log]执行SQL异常：{}", sql);
                LOGGER.error("[log]异常", e);
                throw e;
            }
            long end = System.currentTimeMillis();
            long time = (end - start);
            LOGGER.debug("<==   Sql Cost Time: " + time);

            return returnValue;
        } else if (target instanceof ResultSetHandler) {
            Statement stmt = (Statement) invocation.getArgs()[0];
            List<Object> returnValue = (List<Object>) invocation.proceed();
            handleResult(returnValue);
            return returnValue;
        }
        return invocation.proceed();
    }

    public static void handleResult(List<Object> returnValue) {
        LOGGER.debug("<==           Total: " + returnValue.size());
        for (Object bean : returnValue) {
            String beanStr = ToStringBuilder.reflectionToString(bean, ToStringStyle.DEFAULT_STYLE);
            LOGGER.debug("<==             Row: " + beanStr);
        }
    }

    public static String getSql(Configuration configuration, BoundSql boundSql, String sqlId) {
        String sql = showSql(configuration, boundSql);
        StringBuilder str = new StringBuilder(100);
        str.append("[" + sqlId + "]");
        str.append("=");
        str.append(sql);
        return str.toString();
    }

    private static String getParameterValue(Object obj) {
        String value = null;
        if (obj instanceof String) {
            value = "'" + obj.toString().replaceAll("'", "''") + "'";
        } else if (obj instanceof Date) {
            //DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            value = "to_date('" + sdf.format((Date) obj) + "','yyyy-mm-dd hh24:mi:ss')";
        } else {
            if (null != obj) {
                value = obj.toString();
            } else {
                value = "null";
            }
        }
        return value;
    }

    public static String showSql(Configuration configuration, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (parameterMappings.size() > 0) {
            if (parameterObject == null) {
                return sql + ": config is null";
            }
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));
            } else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);

                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    }
                }
            }
        }
        return sql;

    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);

    }

    @Override
    public void setProperties(Properties properties) {
        String debugStr = properties.getProperty("show_log");
        this.showSql = "true".equalsIgnoreCase(debugStr);
    }

}
