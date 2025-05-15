package com.project.spring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.project.spring.filter.JWTAuthenticationFilter;
import com.project.spring.service.MainUserDetailService;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	
	@Autowired
	private MainUserDetailService userDetailsService;
	@Autowired
	private JWTAuthenticationFilter jwtFilter;
	
	@Bean
	public SecurityFilterChain  securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
		.authorizeHttpRequests(
				req->req.requestMatchers("/","/mainUser","/mainUser/*").permitAll()
				.anyRequest().authenticated()
				
				)
		.httpBasic(Customizer.withDefaults())
		//.formLogin(Customizer.withDefaults())
		  .formLogin(form -> form
		            .loginPage("/") // Your custom login page
		            .loginProcessingUrl("/loginProject") // Form POST endpoint
		            .defaultSuccessUrl("/home", true) // Redirect after login
		            .permitAll()
		        )
		//.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
		  .oauth2Login(oauth2 -> oauth2
		            .loginPage("/")             // OAuth2 login uses '/'
		            .defaultSuccessUrl("/home", true)
		        )
		.addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class);
		
		
		return http.build();

	}
	
	
	@Bean
	public AuthenticationProvider  authenticationProvider() {
		DaoAuthenticationProvider provider= new DaoAuthenticationProvider();
		provider.setPasswordEncoder(new BCryptPasswordEncoder());
	//	provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
		provider.setUserDetailsService(userDetailsService);
		return provider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration autoConfig) throws Exception {
		return autoConfig.getAuthenticationManager();
	}	
	
	
	
}
