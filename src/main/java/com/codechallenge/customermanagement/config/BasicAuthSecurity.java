package com.codechallenge.customermanagement.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.codechallenge.customermanagement.config.security.basic.PasswordEncoderConfig;
import com.codechallenge.customermanagement.config.security.basic.RestAuthenticationEntryPoint;
import com.codechallenge.customermanagement.constant.Constants;

/**
 * Configuration class for setting up basic authentication security.
 * 
 * TODO BasicAuthSecurity should be moved to a common library and not implemented in every project
 */
@Configuration
@EnableWebSecurity
public class BasicAuthSecurity {


  private final RestAuthenticationEntryPoint authenticationEntryPoint;

  /**
   * Constructor to initialize BasicAuthSecurity with a RestAuthenticationEntryPoint.
   *
   * @param authenticationEntryPoint the authentication entry point to handle unauthorized access
   */
  @Autowired
  public BasicAuthSecurity(RestAuthenticationEntryPoint authenticationEntryPoint) {
    this.authenticationEntryPoint = authenticationEntryPoint;
  }

  /**
   * Configures the security filter chain.
   *
   * @param http the HttpSecurity configuration
   * @return the configured SecurityFilterChain
   * @throws Exception if an error occurs while configuring security
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf().disable();

    http.authorizeRequests()
        .antMatchers("/public/**","/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .httpBasic()
        .authenticationEntryPoint(authenticationEntryPoint);
    return http.build();
  }
  
  /**
   * Configures in-memory authentication with a user and roles.
   *
   * @param auth the AuthenticationManagerBuilder to configure
   * @param passwordEncoder the PasswordEncoder to encode passwords
   * @throws Exception if an error occurs while configuring authentication
   */
  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth,PasswordEncoder passwordEncoder) throws Exception {
    auth.inMemoryAuthentication()
	    .passwordEncoder(PasswordEncoderConfig.passwordEncoder())
	    .withUser(Constants.USER)
	    .password(PasswordEncoderConfig.passwordEncoder().encode(Constants.PASSWORD))
	    .roles(Constants.ROLE);
  }

}