package com.assignment.CryptoAPI.Security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;




@Configuration
@EnableWebSecurity
public class securityConfig {
    @Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((authorize) -> authorize
				// .requestMatchers(new AntPathRequestMatcher("/api/token","GET")).permitAll()
                // .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
				.anyRequest().permitAll()
				
               
			);
        http.csrf(csrf -> csrf
			.ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**"))
			.ignoringRequestMatchers(new AntPathRequestMatcher("/api/**"))
			);
		http.headers(header->header
			.frameOptions(frameOptions->frameOptions
				.disable()
				)
			);
        http.addFilterBefore(new CustomFilter(), BasicAuthenticationFilter.class);
		return http.build();
	}
   
}


