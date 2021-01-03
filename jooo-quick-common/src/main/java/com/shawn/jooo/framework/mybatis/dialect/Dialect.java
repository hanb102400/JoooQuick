package com.shawn.jooo.framework.mybatis.dialect;

/**
 * 数据库方言处理接口
 *
 * @author hanbing
 */
public interface Dialect {

    String buildCountSql(String sql);

    String buildSortSql(String sql, String sort);

    String buildPaginationSql(String sql, long offset, long limit);
}
