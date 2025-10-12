package com.sKribble.api.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.sKribble.api.security.jwt.JwtFilter;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtFilter jwtFilter;

	@Value("${SKribble.frontend.origin}")
	private String frontendOrigin;
	
	@Value("${spring.graphql.http.path}")
	private String graphqlPath;
	
	@Value("${SKribble.auth.path}")
	private String authPath;

	@Value("${SKribble.common.service.path}")
	private String commonServicePath;
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.httpBasic(Customizer.withDefaults());
		
		http.csrf(c -> c.disable());

		http.cors(c -> {
			CorsConfigurationSource source = new CorsConfigurationSource() {
				@Override
				public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
					CorsConfiguration config = new CorsConfiguration();
                    config.setAllowCredentials(true);
                    config.addAllowedOrigin(frontendOrigin);
                    config.addAllowedHeader("*");
                    config.addAllowedMethod("*");
					return config;
				}
			};
			c.configurationSource(source);
		});
		
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		
		http.authorizeHttpRequests(c -> {
			c.requestMatchers(graphqlPath + "/**", commonServicePath + "/**").hasAnyRole("USER");
			c.requestMatchers(authPath + "/**").permitAll();
		});
		
		return http.build();
	}
}
