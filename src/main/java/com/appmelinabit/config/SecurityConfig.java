package com.appmelinabit.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity 
public class SecurityConfig {

    @Autowired
    private CustomAuthenticationSuccessHandler customSuccessHandler; 

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

            .authorizeHttpRequests(authorize -> authorize

                .requestMatchers(
                    "/login", 
                    "/esqueceu-senha", 
                    "/redefinir-senha", 
                    "/cadastro", 
                    "/css/**", 
                    "/js/**"
                ).permitAll()
                

                .requestMatchers("/gerenciar/**").hasAnyRole("ADMIN", "USER") 
                

                .requestMatchers("/admin/**").hasRole("ADMIN") 
                .requestMatchers("/dashboard/**").hasAnyRole("ADMIN", "USER")
                

                .anyRequest().authenticated()
            )
            

            .formLogin(form -> form
                .loginPage("/login") 
                .failureUrl("/login?error=true")
                .permitAll()
                .successHandler(customSuccessHandler) 
            )
            

            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .permitAll()
            )
            

            .csrf(csrf -> csrf.disable()); 

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}