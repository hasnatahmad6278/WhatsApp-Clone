package com.whattsApp.whatsappClone.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class AppConfig {
	
/*	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and().authorizeHttpRequests(authorize -> authorize.requestMatchers("/api/**").authenticated()
				.anyRequest().permitAll()).addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
			.csrf().disable()
			.cors().configurationSource(new CorsConfigurationSource() {
				
				@Override
				public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
					CorsConfiguration cfg = new CorsConfiguration();
					cfg.setAllowedOrigins(Arrays.asList("http://localhost:3000/"));
					cfg.setAllowedMethods(Collections.singletonList("*"));
					cfg.addAllowedMethod("OPTIONS");
					cfg.addAllowedMethod("GET");
					cfg.addAllowedMethod("POST");
					cfg.addAllowedMethod("PUT");
					cfg.addAllowedMethod("DELETE");
					cfg.addAllowedMethod("OPTIONS");
					cfg.setAllowCredentials(true);
					cfg.setAllowedHeaders(Collections.singletonList("*"));
					cfg.setExposedHeaders(Arrays.asList("Authorization"));

					cfg.setMaxAge(3600L);
					return cfg;
				}
				
			})
			.and().formLogin().and().httpBasic();
		return http.build();
	}
*/
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.sessionManagement(management->management.sessionCreationPolicy(
           SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(Authorize -> Authorize
			.requestMatchers("/api/**").authenticated()
			.anyRequest().permitAll())
			.addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
			.csrf(csrf -> csrf.disable())
		    .cors().configurationSource(corsConfigurationSource()); 
				
			
		return http.build();
	}
	
	 @Bean
	    public CorsConfigurationSource corsConfigurationSource() {
	        CorsConfiguration configuration = new CorsConfiguration();
	        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
	        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
	        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept"));
	        configuration.setExposedHeaders(Arrays.asList("Authorization"));
	        configuration.setAllowCredentials(true);

	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", configuration);

	        return source;
	    }

	
	
	

	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
