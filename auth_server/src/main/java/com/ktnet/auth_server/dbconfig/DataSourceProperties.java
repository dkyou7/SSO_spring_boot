package com.ktnet.auth_server.dbconfig;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties
public class DataSourceProperties {

    @Bean(name = "tradeDataSource")
    @Qualifier("tradeDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.hikari.trade")
    public DataSource tradeDataSource(){
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(name = "ssoDataSource")
    @Qualifier("ssoDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.hikari.sso")
    public DataSource ssoDataSource(){
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }
}
