package com.shawn.jooo.framework.mybatis.dialect;

/**
 * @author shawn
 */
public class DialectFactory {

    private static final String MYSQL_DIALECT = "mysql";

    private static final String ORACLE_DIALECT = "oracle";

    public static Dialect getDialect(String dialectName) throws Exception {

        if (MYSQL_DIALECT.equals(dialectName)) {
            return new MysqlDialect();
        } else if (ORACLE_DIALECT.equals(dialectName)) {
            return new OracleDialect();
        } else {
            throw new Exception("Cannot get the dialect : " + dialectName);
        }

    }
}
