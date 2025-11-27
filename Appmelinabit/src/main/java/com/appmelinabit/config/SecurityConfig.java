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
            // Configuração de Autorização de URLs
            .authorizeHttpRequests(authorize -> authorize
                // Páginas de acesso público
                .requestMatchers(
                    "/login", 
                    "/esqueceu-senha", 
                    "/redefinir-senha", 
                    // ---> CORREÇÃO AQUI: Adicionar /cadastro
                    "/cadastro", 
                    // <---
                    "/css/**", 
                    "/js/**"
                ).permitAll()
                
                // Restringe o acesso aos painéis com base no papel
                .requestMatchers("/admin/**").hasRole("ADMIN") 
                .requestMatchers("/dashboard/**").hasAnyRole("ADMIN", "USUARIO_COMUM") 
                
                // Qualquer outra requisição exige autenticação
                .anyRequest().authenticated()
            )
            
            // Configuração do Formulário de Login
            .formLogin(form -> form
                .loginPage("/login") 
                .failureUrl("/login?error=true")
                .permitAll()
                .successHandler(customSuccessHandler) 
            )
            
            // Configuração de Logout
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .permitAll()
            )
            
            // Recomendações de segurança
            .csrf(csrf -> csrf.disable()); 

        return http.build();
    }

    // Bean para o codificador de senha (obrigatório)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}