package com.nnk.springboot.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.nnk.springboot.services.CustomUserDetailService;


/**
 * Configuration de la sécurité Spring Security.
 * Cette classe définit la gestion des utilisateurs et les règles d'accès aux endpoints.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private Logger logger = LogManager.getLogger(SecurityConfig.class);

    @Autowired
    private CustomUserDetailService customUserDetailService;

    /**
     * Définit les règles de sécurité des endpoints de l'application.
     * 
     * @param http l'objet HttpSecurity utilisé pour configurer la sécurité
     * @return l'instance SecurityFilterChain configurée
     * @throws Exception si une erreur survient lors de la configuration
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        logger.info("Configuration des règles de sécurité");

        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
        		.requestMatchers("/app/login", "/app/signup", "/user/validate", "/home", "/403", "/cc/**").permitAll()
                .requestMatchers("/trade/**", "/rating/**", "/ruleName/**", "/user/**").hasRole("ADMIN")
                .requestMatchers("/bidList/**", "/curvePoint/**").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated())
            .formLogin(form -> form.loginPage("/app/login")
                .defaultSuccessUrl("/", true)
                .permitAll())
            .logout(logout -> logout
            		.logoutUrl("/logout")
            		.logoutSuccessUrl("/app/login?logout")
            		.permitAll())
            .exceptionHandling(ex -> ex.accessDeniedPage("/403"));

        logger.info("Configuration de la sécurité appliquée avec succès");

        return http.build();
    }

    /**
     * Définit le bean BCryptPasswordEncoder pour encoder les mots de passe.
     * 
     * @return une instance de BCryptPasswordEncoder
     */
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        logger.debug("Initialisation du BCryptPasswordEncoder");
        return new BCryptPasswordEncoder();
    }

    /**
     * Configure le gestionnaire d'authentification avec un encodage des mots de passe et un service de gestion des utilisateurs.
     * 
     * @param http l'objet HttpSecurity utilisé pour configurer la sécurité
     * @param bCryptPasswordEncoder l'encodeur de mot de passe BCrypt
     * @return une instance d'AuthenticationManager configurée
     * @throws Exception si une erreur survient lors de la configuration
     */
    @Bean
    AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
        logger.info("Configuration du gestionnaire d'authentification");

        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailService).passwordEncoder(bCryptPasswordEncoder);

        logger.info("Gestionnaire d'authentification configuré avec succès");

        return authenticationManagerBuilder.build();
    }
}