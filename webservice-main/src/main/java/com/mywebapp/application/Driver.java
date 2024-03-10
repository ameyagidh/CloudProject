package com.mywebapp.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Main class to start the application.
 */
@SpringBootApplication
@ComponentScan(basePackages = { "com.mywebapp.*"})
@EnableJpaRepositories
public class Driver {
    private static final Logger LOGGER = LoggerFactory.getLogger(Driver.class);

    public static void main(String[] args) {
        SpringApplication.run(Driver.class, args);
        LOGGER.info("Application started");
    }

    /**
     * Bean definition for password encoder.
     *
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
