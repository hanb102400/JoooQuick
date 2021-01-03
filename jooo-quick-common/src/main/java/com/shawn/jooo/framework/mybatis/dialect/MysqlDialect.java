package com.shawn.jooo.framework.mybatis.dialect;

/**
 * Mysql方言实现类
 *
 * @author shawn
 */
public class MysqlDialect implements Dialect {

    @Override
    public String buildCountSql(String sql) {
        return "select count(*) from (" + sql + ") as t_total";
    }

    @Override
    public String buildSortSql(String sql, String sort) {
        if (sort != null && !"".equals(sort)) {
            return sql + " order by " + sort;
        } else {
            return sql;
        }
    }

    @Override
    public String buildPaginationSql(String sql, long offset, long limit) {
        StringBuilder paginationSql = new StringBuilder();
        if (offset > 0) {
            paginationSql.append(sql).append(" limit ").append(offset).append(",").append(limit);
        } else {
            paginationSql.append(sql).append(" limit ").append(limit);
        }
        return paginationSql.toString();
    }
}
