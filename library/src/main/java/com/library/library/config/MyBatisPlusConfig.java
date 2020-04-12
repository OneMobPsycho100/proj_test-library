package com.library.library.config;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.ClassUtils;

import javax.sql.DataSource;

/**
 * @Author: chenmingzhe
 * @Date: 2020/4/12 16:16
 */
@Configuration
@MapperScan(value = "com.library.library.sys.mapper", sqlSessionFactoryRef = "sysSqlSessionFactory")
public class MyBatisPlusConfig {

    @Bean("sysSqlSessionFactory")
    public SqlSessionFactory sysSqlSessionFactory(@Qualifier("sysDataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        sqlSessionFactory.setConfiguration(configuration);
        sqlSessionFactory.setMapperLocations(
                new PathMatchingResourcePatternResolver()
                        .getResources(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                                + ClassUtils.convertClassNameToResourcePath("com.library.library.sys.mapper.xml") + "/*.xml"));
//        sqlSessionFactory.setPlugins(new PaginationInterceptor(),
//                new OptimisticLockerInterceptor());
        return sqlSessionFactory.getObject();
    }

    @Primary
    @Bean("libSqlSessionFactory")
    public SqlSessionFactory libSqlSessionFactory(@Qualifier("libDataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean sessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                        + "com/library/library/mapper/**/*.xml"));
        return sessionFactoryBean.getObject();
    }
}
