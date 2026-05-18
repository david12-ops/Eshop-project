package com.example.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.example.service_interface.UserService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)
            throws Exception {

        http

                // =====================================================
                // AUTHORIZATION
                // =====================================================

                .authorizeHttpRequests(requests -> requests

                        // public pages
                        .requestMatchers(
                                "/",
                                "/home",
                                "/login",
                                "/403",
                                "/css/**",
                                "/js/**",
                                "/images/**")
                        .permitAll()

                        // admin only
                        .requestMatchers(
                                "/admin/**")
                        .hasRole("ADMIN")

                        // authenticated pages
                        .requestMatchers(
                                "/products/**",
                                "/categories/**",
                                "/orders/**",
                                "/customers/**",
                                "/users/**",
                                "/invoices/**")
                        .authenticated()

                        // everything else
                        .anyRequest()
                        .authenticated())

                // =====================================================
                // LOGIN
                // =====================================================

                .formLogin(form -> form

                        // thymeleaf login page
                        .loginPage("/login")

                        // POST action url
                        .loginProcessingUrl("/login")

                        // redirect after login
                        .defaultSuccessUrl("/home", true)

                        // login failed
                        .failureUrl("/login?error=true")

                        .permitAll())

                // =====================================================
                // LOGOUT
                // =====================================================

                .logout(logout -> logout

                        .logoutUrl("/logout")

                        .logoutSuccessUrl("/login?logout=true")

                        .invalidateHttpSession(true)

                        .deleteCookies("JSESSIONID")

                        .permitAll())

                // =====================================================
                // ACCESS DENIED
                // =====================================================

                .exceptionHandling(exceptions -> exceptions

                        .accessDeniedHandler(
                                accessDeniedHandler()))

                .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.sendRedirect("/403");
        };
    }
}