package com.shawn.jooo.framework.mybatis.dialect;

/**
 *  OracleDialect
 * 
 *  @author shawn
 */
public class OracleDialect implements Dialect {

    @Override
    public String buildCountSql(String sql) {
        return "select count(*) from (" + sql + ") t_total";
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
        offset++;
        paginationSql.append("select * from ( SELECT U.*, ROWNUM RNUM from ( ")
                .append(sql).append(") U where ROWNUM <= ")
                .append(offset + limit).append(") where RNUM > ").append(offset);
        return paginationSql.toString();
    }
}
