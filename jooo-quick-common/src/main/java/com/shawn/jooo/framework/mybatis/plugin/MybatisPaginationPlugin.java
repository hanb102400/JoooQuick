package com.shawn.jooo.framework.mybatis.plugin;


import com.shawn.jooo.framework.core.page.Pageable;
import com.shawn.jooo.framework.mybatis.dialect.Dialect;
import com.shawn.jooo.framework.mybatis.dialect.DialectFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.*;
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
 * Mybatis分页插件,方言：dialect：mysql/oracle
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

            //判断是否默认分页
            RowBounds rowBounds = (RowBounds) bounds;
            if (bounds instanceof RowBounds) {
                // 不需要分页的场合
                if (rowBounds == null || rowBounds == RowBounds.DEFAULT) {
                    return invocation.proceed();
                }
            }

            // 查询分页记录总数
            if (bounds instanceof Pageable) {
                Pageable pageable = (Pageable) bounds;
                Connection connection = (Connection) invocation.getArgs()[0];
                final String totalCountSql = this.dialect.buildCountSql(originalSql);
                long totalCount = getTotalCount(totalCountSql, connection, mappedStatement, parameterObject, boundSql);
                pageable.setTotalCount(totalCount);
            }

            // 查询分页数据
            String paginationSql = dialect.buildPaginationSql(originalSql, rowBounds.getOffset(), rowBounds.getLimit());
            metaStatementHandler.setValue("delegate.boundSql.sql", paginationSql);

            // 传入默认分页参数
            metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
            metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
        }
        return invocation.proceed();
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
        String KeyProperties = StringUtils.join(ms.getKeyProperties(), ",");
        builder.keyProperty(KeyProperties);
        String keyColumns = StringUtils.join(ms.getKeyColumns(), ",");
        builder.keyColumn(keyColumns);
        builder.databaseId(ms.getDatabaseId());
        return builder.build();
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
            try {
                if (null != rs) {
                    rs.close();
                }
                if (null != pStatement) {
                    pStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
