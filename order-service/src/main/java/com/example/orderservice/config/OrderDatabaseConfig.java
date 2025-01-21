package com.example.orderservice.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

import org.springframework.context.annotation.Primary;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


// OrderDatabaseConfig.java (your existing database)
@Configuration
@EnableJpaRepositories(
        basePackages = "com.example.orderservice.repo.order",
        entityManagerFactoryRef = "orderEntityManagerFactory",
        transactionManagerRef = "orderTransactionManager"
)
public class OrderDatabaseConfig {
    @Primary
    @Bean(name = "orderDataSource")
    // @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource orderDataSource() {
        HikariDataSource source = new HikariDataSource();
        source.setJdbcUrl("jdbc:postgresql://localhost:5432/order-db");
        source.setUsername("postgres");
        source.setPassword("postgres");
        source.setDriverClassName("org.postgresql.Driver");
        return source;
    }

    @Primary
    @Bean(name = "orderEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean orderEntityManagerFactory(
        EntityManagerFactoryBuilder builder,
            @Qualifier("orderDataSource") DataSource dataSource) {

        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        
        return builder
                .dataSource(dataSource)
                .packages("com.example.common.models")
                .properties(properties)
                .persistenceUnit("orders")
                .build();
    }

    @Primary
    @Bean(name = "orderTransactionManager")
    public PlatformTransactionManager orderTransactionManager(
            @Qualifier("orderEntityManagerFactory") EntityManagerFactory orderEntityManagerFactory) {

        return new JpaTransactionManager(orderEntityManagerFactory);
    }
}