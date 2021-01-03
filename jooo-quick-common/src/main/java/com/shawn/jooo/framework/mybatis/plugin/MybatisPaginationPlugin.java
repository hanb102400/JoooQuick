package com.shawn.jooo.framework.mybatis.plugin;


import com.shawn.jooo.framework.mybatis.dialect.Dialect;
import com.shawn.jooo.framework.mybatis.dialect.DialectFactory;
import com.shawn.jooo.framework.page.Pageable;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Mybatis分页插件
 *
 * @author shawn
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class MybatisPaginationPlugin implements Interceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MybatisPaginationPlugin.class);

    private Dialect dialect;

    /**
     * 拦截器方法执行
     *
     * @param invocation
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        return this.processPage(invocation);
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {
        try {
            this.dialect = DialectFactory.getDialect(properties.getProperty("dialect"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Mybatis拦截器方法，拦截Page,RowBounds参数
     *
     * @param invocation 拦截器入参
     * @return 返回执行结果
     * @throws Throwable 抛出异常
     */
    private Object processPage(Invocation invocation) throws Throwable {

        Object target = invocation.getTarget();
        if (target instanceof StatementHandler) {
            StatementHandler statementHandler = (StatementHandler) target;
            MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
            // 修复父类转子类时属性丢失问题
            MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
            BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
            Object bounds = metaStatementHandler.getValue("delegate.rowBounds");
            String originalSql = boundSql.getSql().trim();
            Object parameterObject = boundSql.getParameterObject();

            RowBounds rowBounds = (RowBounds) bounds;
            // 判断分页参数：Pageable类型参数
            if (bounds instanceof Pageable) {
                Pageable pageable = (Pageable) bounds;
                Connection connection = (Connection) invocation.getArgs()[0];
                final String totalCountSql = this.dialect.buildCountSql(originalSql);
                long totalCount = getTotalCount(totalCountSql, connection, mappedStatement, parameterObject, boundSql);
                pageable.setTotalCount(totalCount);
            }

            //判断是否默认分页：RowBounds类型参数
            if (bounds instanceof RowBounds) {
                // 不需要分页的场合
                if (rowBounds == null || rowBounds == RowBounds.DEFAULT) {
                    return invocation.proceed();
                }
            }
            // 查询分页数据
            String paginationSql = dialect.buildPaginationSql(originalSql, rowBounds.getOffset(), rowBounds.getLimit());
            metaStatementHandler.setValue("delegate.boundSql.sql", paginationSql);

            // 禁用内存分页
            metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
            metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
        }
        return invocation.proceed();
    }

    /**
     * Title: buildMappedStatement
     * Description: 生成新的mappedStatement
     *
     * @param ms       原始ms
     * @param boundSql 原始boundsql
     * @param sql      分页最终sql
     * @return 新的mappedStatement
     */

    private MappedStatement buildMappedStatement(MappedStatement ms, BoundSql boundSql, String sql) {

        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(),
                new BoundSqlSqlSource(ms, boundSql, sql), ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.fetchSize(ms.getFetchSize());
        builder.timeout(ms.getTimeout());
        builder.statementType(ms.getStatementType());
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());
        builder.keyGenerator(ms.getKeyGenerator());
        builder.keyProperty(delimitedArraytoString(ms.getKeyProperties()));
        builder.keyColumn(delimitedArraytoString(ms.getKeyColumns()));
        builder.databaseId(ms.getDatabaseId());
        return builder.build();
    }

    /**
     * 获取分页参数
     *
     * @param params RowBounds参数
     * @return 返回Page对象
     */
    public RowBounds getPageFromRowBounds(Object params) {
        RowBounds rowBounds = null;
        if (params instanceof RowBounds) {
            rowBounds = (RowBounds) params;
        }
        return rowBounds;
    }

    /**
     * 对象中获取分页参数
     *
     * @param params
     * @return
     */
    public Pageable getPageFromParam(Object params) {
        Pageable pageable = null;
        if (params instanceof Pageable) { // 只有一个参数的情况
            pageable = (Pageable) params;
        }
        return pageable;
    }

    /**
     * Title: delimitedArraytoString
     * Description: 将参数转string
     *
     * @param in properties
     * @return keyProperty
     */
    private static String delimitedArraytoString(String[] in) {
        if (in == null || in.length == 0) {
            return null;
        } else {
            StringBuffer answer = new StringBuffer();
            for (String str : in) {
                answer.append(str).append(",");
            }
            return answer.toString();
        }
    }

    /**
     * getTotalCount
     *
     * @param totalCountSql
     * @param connection
     * @param statement
     * @param parameterObj
     * @param boundSql
     * @return
     * @throws SQLException
     */
    public int getTotalCount(final String totalCountSql, final Connection connection, final MappedStatement statement, final Object parameterObj, final BoundSql boundSql) throws SQLException {
        PreparedStatement pStatement = null;
        ResultSet rs = null;
        try {
            pStatement = connection.prepareStatement(totalCountSql);
            setParameters(pStatement, this.buildMappedStatement(statement, boundSql, totalCountSql), boundSql, parameterObj);
            rs = pStatement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            releaseResource(rs, pStatement);
        }
        return 0;
    }


    /**
     * 对SQL参数(?)设值,参考org.apache.ibatis.executor.parameter.DefaultParameterHandler
     * //参考  org.apache.ibatis.scripting.defaults.DefaultParameterHandler
     *
     * @param ps
     * @param mappedStatement
     * @param boundSql
     * @param parameterObject
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql, Object parameterObject) throws SQLException {
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
        parameterHandler.setParameters(ps);
    }

    /**
     * releaseResource
     *
     * @param rs
     * @param ps
     */
    public static void releaseResource(ResultSet rs, PreparedStatement ps) {
        try {
            if (null != rs) {
                rs.close();
                rs = null;
            }
            if (null != ps) {
                ps.close();
                ps = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Title: getMappedStatement
     * Description: 获取 MappedStatement
     *
     * @param invocation 调用
     * @return MappedStatement
     */
    private MappedStatement getMappedStatement(Invocation invocation) {
        return (MappedStatement) invocation.getArgs()[0];
    }

    /**
     * Title: setMappedStatement
     * Description: 设置MappedStatement
     *
     * @param invocation      调用
     * @param mappedStatement 新的MappedStatement
     */
    private void setMappedStatement(Invocation invocation, MappedStatement mappedStatement) {
        invocation.getArgs()[0] = mappedStatement;
    }

    /**
     * Title: getParameter
     * Description: 获取sql入参
     *
     * @param invocation 调用
     * @return sql入参
     */
    private Object getParameter(Invocation invocation) {
        return invocation.getArgs()[1];
    }

    /**
     * Title: getRowBounds
     * Description: 获取行数
     *
     * @param invocation 调用
     * @return 行数范围
     */
    private RowBounds getRowBounds(Invocation invocation) {
        return (RowBounds) invocation.getArgs()[2];
    }

    /**
     * Title: setRowBounds
     * Description: 设置行数范围
     *
     * @param invocation 调用
     * @param rowBounds  行数范围
     */
    private void setRowBounds(Invocation invocation, RowBounds rowBounds) {
        invocation.getArgs()[2] = rowBounds;
    }

    /**
     * ClassName: BoundSqlSqlSource
     * Description: boundsql处理类
     *
     * @author linyl linyuliang.85@gmail.com
     */
    public static class BoundSqlSqlSource implements SqlSource {

        /**
         * boundsql
         */
        private final BoundSql boundSql;

        /**
         * Title: 构造函数
         * Description:根据新的ms和boundsql生成sqlsource
         *
         * @param ms       新的ms
         * @param boundSql 新的boundsql
         * @param sql      执行sql
         */
        public BoundSqlSqlSource(MappedStatement ms, BoundSql boundSql, String sql) {
            this.boundSql = buildBoundSql(ms, boundSql, sql);
        }

        @Override
        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }

        /**
         * Title: buildBoundSql
         * Description: 入参处理
         *
         * @param ms       新的ms
         * @param boundSql 新的boundsql
         * @param sql      执行sql
         * @return 最终boundsql
         */
        private BoundSql buildBoundSql(MappedStatement ms, BoundSql boundSql, String sql) {
            BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
            for (ParameterMapping mapping : boundSql.getParameterMappings()) {
                String prop = mapping.getProperty();
                if (boundSql.hasAdditionalParameter(prop)) {
                    newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
                }
            }
            return newBoundSql;
        }
    }
}
