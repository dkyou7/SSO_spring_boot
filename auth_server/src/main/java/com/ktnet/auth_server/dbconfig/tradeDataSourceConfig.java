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
        entityManagerFactoryRef = "tradeEntityManagerFactory",
        transactionManagerRef = "tradeTransactionManager",
        basePackages = {"com.ktnet.auth_server.UTH_Member"})
public class tradeDataSourceConfig {

    @Autowired
    @Qualifier("tradeDataSource")
    private DataSource tradeDataSource;

    @Primary
    @Bean(name = "tradeEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean tradeEntityManagerFactory(EntityManagerFactoryBuilder builder){
        return builder.dataSource(tradeDataSource).packages("com.ktnet.auth_server.UTH_Member").persistenceUnit("member").build();
    }

    @Primary
    @Bean("tradeTransactionManager")
    public PlatformTransactionManager tradeTransactionManager(EntityManagerFactoryBuilder builder){
        return new JpaTransactionManager(tradeEntityManagerFactory(builder).getObject());
    }
}
