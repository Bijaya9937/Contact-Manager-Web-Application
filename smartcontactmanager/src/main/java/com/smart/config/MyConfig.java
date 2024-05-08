	package com.smart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class MyConfig{
	
	@Bean
	public UserDetailsService getUserDetailsService()
	{
		return new UserDetailsServiceImpl();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider()
	{
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(this.getUserDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		
		return daoAuthenticationProvider;
	}
	
	
	
	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception
	{
        http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests( authorize -> authorize
        		.requestMatchers("/admin/**").hasRole(("ADMIN"))
                .requestMatchers("/user/**").hasRole("USER")
                .requestMatchers("/**").permitAll()
        		)
        		.formLogin(formLogin ->
        			formLogin
        			.loginPage("/signin")
        			.loginProcessingUrl("/do-login")
        			.defaultSuccessUrl("/")
        			.permitAll()
        		);
                
		http.authenticationProvider(authenticationProvider());
		
		return http.build();
	}
	
	
	
}