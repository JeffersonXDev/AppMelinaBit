package com.appmelinabit.service;

import com.appmelinabit.model.Usuario;
import com.appmelinabit.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Usuario buscarUsuarioLogado() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário logado não encontrado no banco."));
    }

    @Transactional
    public Usuario salvarUsuario(Usuario usuario) {
        // CORREÇÃO ESSENCIAL: Só criptografa se a senha NÃO começar com o prefixo do BCrypt ($2a$)
        // Isso impede que, ao mudar o status, o Spring tente criptografar o que já é hash.
        if (usuario.getSenha() != null && !usuario.getSenha().startsWith("$2a$")) {
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }
        
        if (usuario.getIdUsuario() == null) {
            if (usuario.getDataCadastro() == null) {
                usuario.setDataCadastro(LocalDateTime.now());
            }
            // Garante que novos cadastros comecem bloqueados (Admin ativa depois)
            if (usuario.getStatusConta() == null) {
                usuario.setStatusConta("INATIVO");
            }
        }
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario alterarStatusConta(Integer id, String status) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));

        usuario.setStatusConta(status);
        // Usamos o repository diretamente aqui para evitar passar pela lógica de 
        // senha do salvarUsuario e garantir que nada quebre.
        return usuarioRepository.save(usuario);
    }

    // Mantendo todos os métodos originais para não quebrar outras páginas
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
    public void excluirUsuario(Integer id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
        } else {
            throw new RuntimeException("Não foi possível excluir. Usuário não encontrado.");
        }
    }
}