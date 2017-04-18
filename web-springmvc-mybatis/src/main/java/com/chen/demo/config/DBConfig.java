package com.chen.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.ResourceUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author ValleyChen
 * @version 1.0.0
 * @time 2017/1/21
 */
@Configuration
@PropertySource(value="classpath:local/db.properties")
@EnableTransactionManagement
public class DBConfig{


    /**
     * druid datasource spring bean
     *
     * @param dbProp
     * @return
     * @throws SQLException
     */
    @Bean(name = "dataSource",initMethod = "init",destroyMethod = "close")
    public DruidDataSource dataSource(@Qualifier("dbProp") Properties dbProp) throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(dbProp.getProperty("mysql.url"));
        dataSource.setUsername(dbProp.getProperty("mysql.username"));
        dataSource.setPassword(dbProp.getProperty("mysql.password"));
        dataSource.setInitialSize(Integer.valueOf(dbProp.getProperty("initialPoolSize")));
        dataSource.setMinIdle(Integer.valueOf(dbProp.getProperty("minPoolSize")));
        dataSource.setMaxActive(Integer.valueOf(dbProp.getProperty("maxPoolSize")));

        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        dataSource.setMinEvictableIdleTimeMillis(300000);
        dataSource.setValidationQuery("SELECT 'x'");
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);

        dataSource.setPoolPreparedStatements(true);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);

        dataSource.setFilters("stat");
        return dataSource;
    }

    @Bean(name ="jdbcTemplate" )
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        return jdbcTemplate;
    }


    @Bean(name ="pageHelper" )
    public PageHelper pageHelper(){
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("dialect","mysql");
        pageHelper.setProperties(properties);
        return pageHelper;
    }

    @Bean(name ="sqlSessionFactory" )
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource,PageHelper pageHelper) throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);

        Properties properties = new Properties();
        properties.setProperty("cacheEnabled","true");
        properties.setProperty("lazyLoadingEnabled","false");
        properties.setProperty("aggressiveLazyLoading","true");
        properties.setProperty("multipleResultSetsEnabled","true");
        properties.setProperty("useColumnLabel","true");
        properties.setProperty("useGeneratedKeys","true");
        properties.setProperty("autoMappingBehavior","true");
        properties.setProperty("cacheEnabled","FULL");
        properties.setProperty("defaultExecutorType","BATCH");
        properties.setProperty("defaultStatementTimeout","25000");
        sqlSessionFactoryBean.setConfigurationProperties(properties);

        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resourcePatternResolver
                .getResources("classpath*:com/chen/**/*Mapper.xml"));
        sqlSessionFactoryBean.setTypeAliasesPackage("com.chen.*.entity");

        sqlSessionFactoryBean.setPlugins(new Interceptor[]{pageHelper});
        return sqlSessionFactoryBean;
    }


    @Bean(name ="mapperScannerConfigurer" )
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.chen.*.mapper");
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        return mapperScannerConfigurer;
    }

    @Bean(name ="sqlSessionTemplate" )
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH);
    }

    @Bean(name ="transactionManager" )
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }



}
