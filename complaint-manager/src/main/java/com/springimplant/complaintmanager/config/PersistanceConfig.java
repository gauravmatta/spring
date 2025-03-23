package com.springimplant.complaintmanager.config;


import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.sql.init.dependency.DependsOnDatabaseInitialization;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@EnableTransactionManagement
@PropertySource("classpath:database-properties.properties")
@ComponentScan({"com.springimplant.complaintmanager"})
public class PersistanceConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(PersistanceConfig.class);
	
	@Autowired
	private Environment env;

    @Bean
    @DependsOnDatabaseInitialization
    @Autowired
    HibernateTransactionManager transactionManager(SessionFactory sessionFactory)
	{
		HibernateTransactionManager transacManager=new HibernateTransactionManager();
		transacManager.setSessionFactory(sessionFactory);
		return transacManager;
	}

    @Bean(name = "entityManagerFactory")
    @DependsOnDatabaseInitialization
    LocalSessionFactoryBean sessionFactory()
	{
		LocalSessionFactoryBean sessionFactory=new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan(new String[] {"com.springimplant.complaintmanager.entities"});
		sessionFactory.setHibernateProperties(hibernateConfig());
		return sessionFactory;
	}
	
	@Bean
    DataSource dataSource()
	{
		DataSourceBuilder<?> dataSource = DataSourceBuilder.create();
		dataSource.driverClassName(env.getProperty("jdbc.driverCLassName"));
		logger.info(env.getProperty("jdbc.driverCLassName"));
		dataSource.url(env.getProperty("jdbc.url"));
		dataSource.username(env.getProperty("jdbc.user"));
		dataSource.password(env.getProperty("jdbc.pass"));
		return dataSource.build();
	}
	
	Properties hibernateConfig()
	{
		Properties configProp=new Properties();
		configProp.setProperty("hibernate.hdm2ddl.auto",env.getProperty("hibernate.hdm2ddl.auto"));
		System.out.println(env.getProperty("hibernate.hdm2ddl.auto"));
		configProp.setProperty("hibernate.dialect",env.getProperty("hibernate.dialect"));
		configProp.setProperty("hibernate.show_sql",env.getProperty("hibernate.show_sql"));
		return configProp;
	}
}
