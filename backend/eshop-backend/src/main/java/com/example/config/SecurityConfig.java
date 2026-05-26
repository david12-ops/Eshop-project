package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

                                .authorizeHttpRequests(requests -> requests
                                                // PUBLIC
                                                .requestMatchers(
                                                                "/",
                                                                "/login",
                                                                "/register",
                                                                "/error/**",
                                                                "/css/**",
                                                                "/js/**",
                                                                "/images/**")
                                                .permitAll()

                                                // ADMIN ONLY
                                                .requestMatchers(
                                                                "/adminHome/**",
                                                                "/users/**",
                                                                "/roles/**",
                                                                "/app-permissions/**",
                                                                "/error/**",
                                                                "/products/**",
                                                                "/addresses/**",
                                                                "/categories/**",
                                                                "/currencies/**",
                                                                "/customers/**",
                                                                "/discounts/**",
                                                                "/invoices/**",
                                                                "/orders/**",
                                                                "/orderStatuses/**",
                                                                "/paymentMethods/**",
                                                                "/regions/**")
                                                .hasRole("ADMIN")

                                                // ADMIN + STAFF
                                                .requestMatchers(
                                                                "/staffHome/**",
                                                                "/products/**",
                                                                "/addresses/**",
                                                                "/categories/**",
                                                                "/currencies/**",
                                                                "/customers/**",
                                                                "/discounts/**",
                                                                "/invoices/**",
                                                                "/orders/**",
                                                                "/orderStatuses/**",
                                                                "/paymentMethods/**",
                                                                "/regions/**",
                                                                "/error/**")
                                                .hasRole("STAFF")

                                                // CUSTOMER
                                                .requestMatchers(
                                                                "/customerHome/**",
                                                                "/cart/**",
                                                                "/wishlist/**",
                                                                "/error/**")
                                                .hasRole("CUSTOMER")

                                                .anyRequest()
                                                .authenticated())

                                .formLogin(form -> form
                                                .loginPage("/login")
                                                .loginProcessingUrl("/login")
                                                .successHandler((request, response, authentication) -> {
                                                        var authorities = authentication.getAuthorities();
                                                        System.out.println(authorities);

                                                        if (authorities.stream().anyMatch(
                                                                        a -> a.getAuthority().equals("ROLE_ADMIN")))
                                                                response.sendRedirect("/adminHome");
                                                        else if (authorities.stream().anyMatch(
                                                                        a -> a.getAuthority().equals("ROLE_STAFF")))
                                                                response.sendRedirect("/staffHome");
                                                        else if (authorities.stream().anyMatch(
                                                                        a -> a.getAuthority().equals("ROLE_CUSTOMER")))
                                                                response.sendRedirect("/customerHome");
                                                        else
                                                                response.sendRedirect("/login?error=true");
                                                })
                                                .failureUrl("/login?error=true")
                                                .permitAll())

                                .logout(logout -> logout
                                                .logoutUrl("/logout")
                                                .logoutSuccessUrl("/login?logout=true")
                                                .invalidateHttpSession(true)
                                                .deleteCookies("JSESSIONID")
                                                .permitAll());

                return http.build();
        }

        @Bean
        public AccessDeniedHandler accessDeniedHandler() {
                return (request, response, accessDeniedException) -> {
                        response.sendRedirect("/403");
                };
        }
}