package com.appmelinabit.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.appmelinabit.model.Usuario;
import com.appmelinabit.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario salvarUsuario(Usuario usuario) {
        // Você pode adicionar lógicas de negócio aqui, como validações ou criptografia da senha
        return usuarioRepository.save(usuario);
    }
}