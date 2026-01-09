package com.appmelinabit.service;

import com.appmelinabit.model.Usuario;
import com.appmelinabit.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Adicionado
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate; // Adicionado
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // Adicionado

    @Transactional
    public Usuario salvarUsuario(Usuario usuario) {
        // 1. Criptografa a senha
        if (usuario.getSenha() != null && !usuario.getSenha().isEmpty()) {
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }

        // 2. CORREÇÃO DO ERRO DE TIPO:
        // Se no Model for LocalDateTime, use LocalDateTime.now()
        if (usuario.getIdUsuario() == null && usuario.getDataCadastro() == null) {
            usuario.setDataCadastro(java.time.LocalDateTime.now());
        }

        return usuarioRepository.save(usuario);
    }

    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email).orElse(null);
    }

    public List<Usuario> listarTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(Integer id) {
        return usuarioRepository.findById(id);
    }

    @Transactional
    public Usuario alterarStatusConta(Integer id, String status) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));

        usuario.setStatusConta(status);
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void excluirUsuario(Integer id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
        } else {
            throw new RuntimeException("Não foi possível excluir. Usuário não encontrado.");
        }
    }
}