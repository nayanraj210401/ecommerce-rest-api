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

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.example.orderservice.repo",
        entityManagerFactoryRef = "productEntityManagerFactory",
        transactionManagerRef = "productTransactionManager"
)
public class ProductDatabaseConfig {

    @Bean(name = "productDataSource")
    @ConfigurationProperties(prefix = "spring.product-datasource")
    public DataSource productDataSource() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "productEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean productEntityManagerFactory(
            @Qualifier("productDataSource") DataSource dataSource) {

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.example.common.models");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean(name = "productTransactionManager")
    public PlatformTransactionManager productTransactionManager(
            @Qualifier("productEntityManagerFactory") EntityManagerFactory productEntityManagerFactory) {

        return new JpaTransactionManager(productEntityManagerFactory);
    }
}
