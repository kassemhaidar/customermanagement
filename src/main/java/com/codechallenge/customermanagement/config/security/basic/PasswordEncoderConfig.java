package com.codechallenge.customermanagement.config.security.basic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class for password encoding using BCrypt.
 * 
 * TODO PasswordEncoderConfig should be moved to a common library and not implemented in every project.
 */
@Configuration
public class PasswordEncoderConfig  {

	/**
     * Provides a BCryptPasswordEncoder bean for encoding passwords.
     *
     * @return a BCryptPasswordEncoder instance
     */
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
