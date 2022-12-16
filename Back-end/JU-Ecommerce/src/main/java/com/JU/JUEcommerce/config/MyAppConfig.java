package com.JU.JUEcommerce.config;

import org.springframework.beans.factory.annotation.Value; 
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // we're using this annotation as we want this class to be scanned
public class MyAppConfig implements WebMvcConfigurer {
	
	@Value("${spring.data.rest.base-path}")
	private String basePath;

	@Value("${allowed.origins}")
	private String[] allowedOrigins;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// TODO Auto-generated method stub
		WebMvcConfigurer.super.addCorsMappings(registry);
		
	    
	     // configure cors mapping
		registry.addMapping(this.basePath + "/**").allowedOrigins(this.allowedOrigins);
	}

	
	
}
