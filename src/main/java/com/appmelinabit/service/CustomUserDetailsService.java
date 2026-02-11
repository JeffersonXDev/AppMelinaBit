package com.appmelinabit.service;

import com.appmelinabit.model.Usuario;
import com.appmelinabit.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));

        // Lógica de Ativação: Retorna true apenas se o status for exatamente "ATIVO"
        boolean contaAtiva = "ATIVO".equalsIgnoreCase(usuario.getStatusConta());

        // Usamos o construtor completo do Spring Security:
        // User(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities)
        return new org.springframework.security.core.userdetails.User(
            usuario.getEmail(),
            usuario.getSenha(),
            contaAtiva,      // enabled: Se for false, o Spring bloqueia o login!
            true,            // accountNonExpired
            true,            // credentialsNonExpired
            true,            // accountNonLocked
            Collections.singletonList(new SimpleGrantedAuthority(usuario.getNivel()))
        );
    }
}