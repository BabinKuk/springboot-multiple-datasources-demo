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
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
		entityManagerFactoryRef = "secondaryEntityManagerFactory",
		transactionManagerRef = "secondaryTransactionManager",
		basePackages = {"org.babinkuk.multiple.datasources.demo.repository.secondary"})
public class SecondaryDataSourceConfiguration {
	
	@Autowired
	private Environment env;

	@Bean(name = "secondaryDataSourceProperties")
	@ConfigurationProperties("spring.secondary-datasource")
	public DataSourceProperties secondaryDataSourceProperties() {
		return new DataSourceProperties();
	}
	
	@Bean(name = "secondaryDataSource")
    public DataSource secondaryDataSource() {
    	DataSourceProperties secondaryDataSourceProperties = secondaryDataSourceProperties();
		
		return DataSourceBuilder.create()
				.driverClassName(secondaryDataSourceProperties.getDriverClassName())
				.url(secondaryDataSourceProperties.getUrl())
				.username(secondaryDataSourceProperties.getUsername())
				.password(secondaryDataSourceProperties.getPassword())
				.build();
    }
	
	@Bean(name = "secondaryEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean secondaryEntityManagerFactory() {
		
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(secondaryDataSource());
		factory.setPackagesToScan(new String[]{"org.babinkuk.multiple.datasources.demo.entity.secondary"});
		factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		
		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
		jpaProperties.put("hibernate.show-sql", env.getProperty("spring.jpa.show-sql"));
		factory.setJpaProperties(jpaProperties);
		
		return factory;
	}

	@Bean(name = "secondaryTransactionManager")
	public PlatformTransactionManager secondaryTransactionManager(
		@Qualifier("secondaryEntityManagerFactory") EntityManagerFactory secondaryEntityManagerFactory) {
		
		return new JpaTransactionManager(secondaryEntityManagerFactory);
	}

}
