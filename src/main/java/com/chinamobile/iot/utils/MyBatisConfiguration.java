package com.chinamobile.iot.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.Reader;

/**
 * mybaits数据库配置信息
 */
@Configuration
@MapperScan(basePackages="com.chinamobile.iot.dao")//防止dao注入失败
public class MyBatisConfiguration {

    @Bean
    SqlSessionFactory createSqlSessionFactory() throws IOException {
        String resource = "config/mybatis.xml";
        Reader reader = Resources.getResourceAsReader(resource);
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(reader);
        return factory;
    }

}
