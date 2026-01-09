package com.appmelinabit.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.List; // Faltava este import
import java.util.stream.Collectors;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // Pega as roles do usuário logado
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        List<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        String redirectUrl = "/dashboard"; // Valor padrão (fallback)

        // Hierarquia de redirecionamento (Sempre verifique o mais potente primeiro)
        if (roles.contains("ROLE_ADMIN") || roles.contains("ADMIN")) {
            redirectUrl = "/admin/dashboard";
        }
        else if (roles.contains("ROLE_USER") || roles.contains("USER")) {
            redirectUrl = "/dashboard";
        }
        else if (roles.contains("ROLE_VISITANTE")) {
            redirectUrl = "/home";
        }

        // Redireciona o usuário
        response.sendRedirect(request.getContextPath() + redirectUrl);
    }
}