package com.project.smarthome.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	public void configureAuth(AuthenticationManagerBuilder auth) throws Exception {
		
		auth
		  .inMemoryAuthentication()
		    .withUser("Faakka")
		    .password("{noop}12345")
		    .roles("USER");
		
	}
	
	@Override
	protected void configure(HttpSecurity httpSec) throws Exception {
		
		httpSec
			.authorizeRequests()
				.antMatchers("/")
				.hasRole("USER")
			.and()
				.formLogin()
				.permitAll();
	} 
}
