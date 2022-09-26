package org.babinkuk.multiple.datasources.demo.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.thymeleaf.util.StringUtils;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
		entityManagerFactoryRef = "primaryEntityManagerFactory",
		transactionManagerRef = "primaryTransactionManager",
		basePackages = {"org.babinkuk.multiple.datasources.demo.repository.primary"})
public class PrimaryDataSourceConfiguration {
	
	@Autowired
    private Environment env;
	
	@Primary
	@Bean(name = "primaryDataSourceProperties")
	@ConfigurationProperties(prefix="spring.primary-datasource")
	public DataSourceProperties primaryDataSourceProperties() {
		return new DataSourceProperties();
	}
	
	@Primary
	@Bean(name = "primaryDataSource")
    public DataSource primaryDataSource() {
    	
		DataSourceProperties primaryDataSourceProperties = primaryDataSourceProperties();
		
		return DataSourceBuilder.create()
				.driverClassName(primaryDataSourceProperties.getDriverClassName())
				.url(primaryDataSourceProperties.getUrl())
				.username(primaryDataSourceProperties.getUsername())
				.password(primaryDataSourceProperties.getPassword())
				.build();
	}
    
	@Primary
	@Bean(name = "primaryEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory() {
    	
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(primaryDataSource());
		factory.setPackagesToScan(new String[]{"org.babinkuk.multiple.datasources.demo.entity.primary"});
		factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		
		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
		jpaProperties.put("hibernate.show-sql", env.getProperty("spring.jpa.show-sql"));
		factory.setJpaProperties(jpaProperties);
		
		return factory;
	}

	@Primary
	@Bean(name = "primaryTransactionManager")
	public PlatformTransactionManager primaryTransactionManager(
		@Qualifier("primaryEntityManagerFactory") EntityManagerFactory primaryEntityManagerFactory) {
		
		return new JpaTransactionManager(primaryEntityManagerFactory);
	}

}
