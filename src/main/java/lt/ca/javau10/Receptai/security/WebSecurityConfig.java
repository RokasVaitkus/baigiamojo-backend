package lt.ca.javau10.Receptai.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lt.ca.javau10.Receptai.security.jwt.AuthTokenFilter;



@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

	 @Autowired
	 UserDetailsService userDetailsService;
	
	 @Autowired
	 AuthenticationEntryPoint unauthorizedHandler;
	 
	 @Bean
	 AuthTokenFilter authenticationJwtTokenFilter() {
	    return new AuthTokenFilter();
	 }
	 
	 @Bean
	 DaoAuthenticationProvider authenticationProvider() {
	      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	       
	      authProvider.setUserDetailsService(userDetailsService);
	      authProvider.setPasswordEncoder(passwordEncoder());
	   
	      return authProvider;
	  }
	 
	 @Bean
	 AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
	    return authConfig.getAuthenticationManager();
	  }
	 
	 @Bean
	 PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	  }
	 
	 
	    @Bean
	    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        http.csrf(csrf -> csrf.disable())
	            .cors(cors -> {
	                cors.configurationSource(corsConfigurationSource());
	            })
	            .authorizeHttpRequests(auth -> {
	                auth.requestMatchers("/api/auth/**").permitAll()
	                    .requestMatchers("/api/user/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
	                    .requestMatchers("/api/admin/**").hasAuthority("ROLE_ADMIN")
	                    .anyRequest().permitAll();
	            })
	            .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
	            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

	        http.authenticationProvider(authenticationProvider());
	        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

	        return http.build();
	    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
	        CorsConfiguration configuration = new CorsConfiguration();
	        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // Your frontend origin
	        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE","PATCH", "OPTIONS")); // Allowed methods
	        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type")); // Allowed headers
	        configuration.setAllowCredentials(true); // Allow credentials (optional)

	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", configuration);
	        return source;
	    }
	}