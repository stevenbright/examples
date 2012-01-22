package com.alexshabanov.sprj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableTransactionManagement
public class OrmConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(){
        final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();

        final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter(){
            {
                setGenerateDdl(true);
            }
        };
        factoryBean.setJpaVendorAdapter(vendorAdapter);
        factoryBean.setPersistenceUnitName("H2-PU");
        factoryBean.setPersistenceXmlLocation("META-INF/persistence.xml");
        //factoryBean.setJpaProperties( this.additionProperties() );

        return factoryBean;
    }

//    @Bean
//    public DataSource dataSource() {
//        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
//
//        dataSource.setDriverClassName("org.h2.Driver");
//        dataSource.setUrl("jdbc:h2:mem:testDb");
//        dataSource.setUsername("sa");
//        dataSource.setPassword("");
//
//        return dataSource;
//    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
        return transactionManager;
    }

}
