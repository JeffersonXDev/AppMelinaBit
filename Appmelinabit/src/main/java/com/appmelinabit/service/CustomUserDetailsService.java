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

        // Apenas use o valor do nível do usuário diretamente do banco de dados
        return new org.springframework.security.core.userdetails.User(
            usuario.getEmail(),
            usuario.getSenha(),
            Collections.singletonList(new SimpleGrantedAuthority(usuario.getNivel())) // CORREÇÃO AQUI
        );
    }
}