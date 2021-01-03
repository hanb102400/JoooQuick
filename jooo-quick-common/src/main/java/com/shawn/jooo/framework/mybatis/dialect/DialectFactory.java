package com.shawn.jooo.framework.mybatis.dialect;
/**
 * @author shawn
 */
public class DialectFactory {

    public static Dialect getDialect(String dialectName) throws Exception{

        if("mysql".equals(dialectName)){
            return new MysqlDialect();
        }
        else if("oracle".equals(dialectName)){
            return new OracleDialect();
        }else{
            throw new Exception("Cannot get the dialect : " + dialectName);
        }

    }
}
