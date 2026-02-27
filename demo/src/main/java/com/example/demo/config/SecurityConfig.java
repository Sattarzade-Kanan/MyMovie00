package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)throws  Exception{
        http.authorizeHttpRequests(auth -> auth

                        .requestMatchers("/swagger-ui/**" , "/v3/api-docs/**")
                        .permitAll()
                //public Pages
                        .requestMatchers("/login", "/register", "/css/**" , "/js/**").permitAll().
                        requestMatchers("/movies/new" , "/movies/edit/**" , "/movies/delete/**")
                        .hasRole("ADMIN")
                        .requestMatchers("/movies/**")
                        .hasAnyRole("USER" , "ADMIN")
                        .anyRequest().authenticated())




                .formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/movies" , true)
                        .failureUrl("/login?error")
                        .permitAll()

                )   .exceptionHandling(ex -> ex.accessDeniedPage("/access-denied"))

                .logout(logout -> logout.logoutSuccessUrl("/login?logout")
                );
return http.build();
    }
}
