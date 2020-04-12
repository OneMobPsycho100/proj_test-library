package com.library.library.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @Author: chenmingzhe
 * @Date: 2020/4/12 15:31
 */
@Configuration
public class MultipleDataSourceConfig {

    /**
     * ------------------主数据源----------------------
     */
    @Primary
    @Bean("libDataSource")
    @Qualifier("libDataSource")
    @ConfigurationProperties("spring.datasource.druid.lib")
    public DataSource libDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * -------------------sys数据源---------------------
     **/
    @Bean("sysDataSource")
    @Qualifier("sysDataSource")
    @ConfigurationProperties("spring.datasource.druid.sys")
    public DataSource sysDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * ---------------TransactionManager----------------
     **/
    @Bean
    @Resource
    public PlatformTransactionManager libManager(@Qualifier("libDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    @Resource
    public PlatformTransactionManager sysManager(@Qualifier("sysDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * ---------------JdbcTemplate----------------
     **/
    @Bean
    public JdbcTemplate libJdbcTemplate(@Qualifier("libDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public JdbcTemplate sysJdbcTemplate(@Qualifier("sysDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
