package org.babinkuk.multiple.datasources.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class AppConfig /*extends WebMvcConfigurerAdapter*/ {
	
	@Bean
	public OpenEntityManagerInViewFilter primaryOpenEntityManagerInViewFilter() {
		OpenEntityManagerInViewFilter osivFilter = new OpenEntityManagerInViewFilter();
		osivFilter.setEntityManagerFactoryBeanName("primaryEntityManagerFactory");
		return osivFilter;
	}
	
	@Bean
	public OpenEntityManagerInViewFilter secondaryOpenEntityManagerInViewFilter() {
		OpenEntityManagerInViewFilter osivFilter = new OpenEntityManagerInViewFilter();
		osivFilter.setEntityManagerFactoryBeanName("secondaryEntityManagerFactory");
		return osivFilter;
	}

//	public AppConfig() {
//		// TODO Auto-generated constructor stub
//	}

}
