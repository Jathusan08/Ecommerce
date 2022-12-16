package com.JU.JUEcommerce.config;

import org.springframework.context.annotation.Bean; 
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

import com.okta.spring.boot.oauth.Okta;

@Configuration 
public class SecurityConfiguration {
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		// protect endpoint /JUEcommerce-api/orders  - only accessible to authenticated users
		http.authorizeRequests(configurer -> configurer.antMatchers("/JUEcommerce-api/orders/**")
														.authenticated())
								.oauth2ResourceServer()
								.jwt();
		
		// add CORS filters
		http.cors();
		
		// add content negotiation strategy to support Okta sending back a friendly response
		http.setSharedObject(ContentNegotiationStrategy.class, new HeaderContentNegotiationStrategy());
		
		
		// force a non-empty response body for 401s to make the response more friendly
		Okta.configureResourceServer401ResponseBody(http);
		
		// disable CSRF Since we are not using Cookies for session tracking
		http.csrf().disable();
		
		return http.build();
		
		
	}

}
