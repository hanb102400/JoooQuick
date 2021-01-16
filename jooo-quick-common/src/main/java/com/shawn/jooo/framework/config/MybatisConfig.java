package com.shawn.jooo.framework.config;

import com.shawn.jooo.framework.mybatis.plugin.MybatisPaginationPlugin;
import com.shawn.jooo.framework.mybatis.plugin.MybatisResultMapPlugin;
import com.shawn.jooo.framework.mybatis.plugin.MybatisSqlLogPlugin;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * mybatis配置
 *
 * @author shawn
 */
@Configuration
@ConditionalOnClass(SqlSessionFactory.class)
public class MybatisConfig implements TransactionManagementConfigurer {

    @Value("${mybatis.mapperLocations}")
    private String mapperLocations;

    @Value("${mybatis.typeAliasesPackage}")
    private String typeAliasesPackage;

    @Value("${mybatis.showSql:false}")
    private String showSql;

    @javax.annotation.Resource
    private DataSource dataSource;

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }


    /**
     * mybatis结果集映射插件
     *
     * @return
     */
    @Bean
    MybatisResultMapPlugin mybatisResultMapPlugin() {
        MybatisResultMapPlugin mybatisResultMapPlugin = new MybatisResultMapPlugin();
        return mybatisResultMapPlugin;
    }

    /**
     * mybatis分页插件
     *
     * @return
     */
    @Bean
    MybatisPaginationPlugin mybatisPaginationPlugin() {
        //Mybatis分页插件
        MybatisPaginationPlugin mybatisStatementPlugin = new MybatisPaginationPlugin();
        Properties prop = new Properties();
        prop.setProperty("dialect", "mysql");
        mybatisStatementPlugin.setProperties(prop);
        return mybatisStatementPlugin;
    }

    /**
     * mybatis日志插件
     *
     * @return
     */
    @Bean
    MybatisSqlLogPlugin mybatisSqlLogPlugin() {
        //日志插件
        MybatisSqlLogPlugin mybatisSqlLogPlugin = new MybatisSqlLogPlugin();
        Properties prop = new Properties();
        prop.setProperty("show_sql", showSql);
        mybatisSqlLogPlugin.setProperties(prop);
        return mybatisSqlLogPlugin;
    }


    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {

        //配置SqlSessionFactoryBean
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setTypeAliasesPackage(typeAliasesPackage);
        //配置xml路径
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        bean.setMapperLocations(resolver.getResources(mapperLocations));
        //配置插件
        bean.setPlugins(new Interceptor[]{
                mybatisResultMapPlugin(),
                mybatisPaginationPlugin(),
                mybatisSqlLogPlugin(),
        });
        return bean.getObject();
    }


    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
}


