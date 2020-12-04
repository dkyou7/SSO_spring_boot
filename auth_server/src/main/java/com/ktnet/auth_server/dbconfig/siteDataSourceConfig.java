package com.ktnet.auth_server.dbconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "ssoEntityManagerFactory",
        transactionManagerRef = "ssoTransactionManager",
        basePackages = {"com.ktnet.auth_server.site"})
public class siteDataSourceConfig {

    @Autowired
    @Qualifier("ssoDataSource")
    private DataSource ssoDataSource;

    @Bean(name = "ssoEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean tradeEntityManagerFactory(EntityManagerFactoryBuilder builder){
        return builder.dataSource(ssoDataSource).packages("com.ktnet.auth_server.site").persistenceUnit("KA_FEDERATION_SITE").build();
    }

    @Bean("ssoTransactionManager")
    public PlatformTransactionManager tradeTransactionManager(EntityManagerFactoryBuilder builder){
        return new JpaTransactionManager(tradeEntityManagerFactory(builder).getObject());
    }
}
