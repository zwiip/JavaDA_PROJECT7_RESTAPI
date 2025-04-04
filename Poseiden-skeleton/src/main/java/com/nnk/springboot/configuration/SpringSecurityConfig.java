package com.nnk.springboot.configuration;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.logging.Logger;

/**
 * Configuration class for Spring Security.
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
    /* VARIABLES */
    private final CustomUserDetailsService customUserDetailsService;
    private final Logger logger = Logger.getLogger(SpringSecurityConfig.class.getName());

    /* CONSTRUCTOR */
    public SpringSecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    /* METHODS */

    /**
     * Configures the security filter chain for the application.
     * This defines the app rules for the access to the URLs, the login and the logout in order to protect its endpoints.
     * @param http the HttpSecurity object to configure.
     * @return the configured SecurityFilterChain.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        logger.fine("Configuring Http security.");
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/", "/login", "/user/validate").permitAll()
                        .requestMatchers("/user/**").hasRole("ADMIN")
                        .requestMatchers( "/curvePoint/**", "/bidList/**", "/rating/**", "/ruleName/**", "/trade/**").hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/app/login")
                        .permitAll()
                        .defaultSuccessUrl("/", true)
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )
                .build();
    }

    /**
     * Provides a BCryptPasswordEncoder bean for encoding password.
     * This allows us to secure a password with the bcrypt algorithm to hash passwords in an irreversible form.
     * @return a new BCryptPassEncoder instance.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        logger.fine("Initializing the password encoder.");
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures and provides an AuthenticationManager bean.
     * This object manage the authentication by checking the given information for a user in order to grant or denied the access to the app ressources..
     * @param http the HttpSecurity object to configure.
     * @param bCryptPasswordEncoder the password encoder to use.
     * @return the configured AuthenticationManager.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http
                .getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);
        return authenticationManagerBuilder.build();
    }
}
