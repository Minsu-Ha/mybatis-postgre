package com.example.mybatis.admin.dataSource.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

@Configuration
@Slf4j
public class DataSourceConfig {

    @Bean(name = "primaryDataSource")
    public DataSource primaryDataSource() {
        DriverManagerDataSource dds = new DriverManagerDataSource();

        dds.setUrl("jdbc:postgresql://localhost:5432/postgres");
        dds.setUsername("mydb");
        dds.setPassword("1234");
        dds.setDriverClassName("org.postgresql.Driver");

        return dds;
    }

    @Bean(name = "secondaryDataSource")
    public DataSource secondaryDataSource() {
        DriverManagerDataSource dds = new DriverManagerDataSource();

        dds.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
        dds.setUsername("crypto");
        dds.setPassword("crypto");
        dds.setDriverClassName("oracle.jdbc.driver.OracleDriver");

        return dds;
    }

    @Bean
    public SqlSessionFactory primarySqlSessionFactory(@Qualifier("primaryDataSource") DataSource primaryDataSource) throws Exception {
        log.info("데이터베이스 연결 URL: " + primaryDataSource.getConnection().getMetaData().getURL());
        log.info("데이터베이스 연결 사용자: " + primaryDataSource.getConnection().getMetaData().getUserName());
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(primaryDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mappers/primary/**/*.xml"));

        return sessionFactory.getObject();
    }

    @Bean("primarySession")
    public SqlSessionTemplate primarySqlSessionTemplate(@Qualifier("primarySqlSessionFactory") SqlSessionFactory primarySqlSessionFactory) {
        return new SqlSessionTemplate(primarySqlSessionFactory);
    }

    @Bean
    public DataSourceTransactionManager primaryTransactionManager(@Qualifier("primaryDataSource") DataSource primaryDataSource) {
        return new DataSourceTransactionManager(primaryDataSource);
    }

    @Bean
    public SqlSessionFactory secondarySqlSessionFactory(@Qualifier("secondaryDataSource") DataSource secondaryDataSource) throws Exception {
        log.info("데이터베이스 연결 URL: " + secondaryDataSource.getConnection().getMetaData().getURL());
        log.info("데이터베이스 연결 사용자: " + secondaryDataSource.getConnection().getMetaData().getUserName());
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(secondaryDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mappers/secondary/**/*.xml"));
        return sessionFactory.getObject();
    }

    @Bean("secondarySession")
    public SqlSessionTemplate secondarySqlSessionTemplate(@Qualifier("secondarySqlSessionFactory") SqlSessionFactory secondarySqlSessionFactory) {
        return new SqlSessionTemplate(secondarySqlSessionFactory);
    }

    @Bean
    public DataSourceTransactionManager secondaryTransactionManager(@Qualifier("secondaryDataSource") DataSource secondaryDataSource) {
        return new DataSourceTransactionManager(secondaryDataSource);
    }

}
