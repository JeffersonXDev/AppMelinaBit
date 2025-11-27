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

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, 
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        
        String redirectUrl = "/dashboard"; // URL padrão para usuario_comum
        
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        
        for (GrantedAuthority authority : authorities) {
            
            // NOTE: O Spring Security normalmente adiciona o prefixo "ROLE_"
            // Verifique se o papel é "ROLE_ADMIN" (ou como você o definiu)
            if (authority.getAuthority().equals("ROLE_ADMIN") || authority.getAuthority().equals("ADMIN")) {
                redirectUrl = "/admin-dashboard"; 
                break;
            }
        }
        
        response.sendRedirect(request.getContextPath() + redirectUrl);
    }
}