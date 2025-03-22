package com.springimplant.mvc.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import com.springimplant.mvc.dao.StudentDao;
import com.springimplant.mvc.model.Account;
import com.springimplant.mvc.model.Student;
import com.springimplant.mvc.model.User;

@Configuration
//@EnableWebMvc
@ComponentScan(basePackages="com.springimplant.mvc")
public class RestServiceConfiguration {
	
	@Bean(name="studentDao")
	public StudentDao getstudentDao() {
		StudentDao dao = new StudentDao();
		dao.setHibernateTemplate(getHibernateTemplate());
		return dao;
	}
	
	@Bean("transactionManager")
	public HibernateTransactionManager getTmanger() {
		HibernateTransactionManager mgr = new HibernateTransactionManager();
		mgr.setSessionFactory(getFactoryBean().getObject());
		return mgr;
	}
	
	@Bean("ds")
	public DriverManagerDataSource ds() {
		DriverManagerDataSource source = new DriverManagerDataSource();
		source.setDriverClassName("com.mysql.cj.jdbc.Driver");
		source.setUrl("jdbc:mysql://localhost:3306/restservice");
		source.setUsername("root");
		source.setPassword("root@04G");
		return source;
	}
	
	
	@Bean(name = "factory")
	public LocalSessionFactoryBean getFactoryBean() {
		LocalSessionFactoryBean bean = new LocalSessionFactoryBean();
		bean.setDataSource(ds());
		Properties props = new Properties();
		props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
		props.setProperty("hibernate.show_sql", "true");
		props.setProperty("hibernate.hbm2ddl.auto", "update");		
		bean.setHibernateProperties(props);
		Class<?> [] annotatedClasses =  {Student.class,Account.class,User.class};
		bean.setAnnotatedClasses(annotatedClasses);
		return bean;
	}
	
	@Bean(name = "hibernateTemplate")
	public HibernateTemplate getHibernateTemplate() {
		HibernateTemplate template = new HibernateTemplate();
		template.setSessionFactory(getFactoryBean().getObject());
		return template;
	}
}