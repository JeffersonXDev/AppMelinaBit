package com.appmelinabit.service;

import com.appmelinabit.model.Usuario;
import com.appmelinabit.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // 1. Busca o usuário que está autenticado na sessão atual
    public Usuario buscarUsuarioLogado() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário logado não encontrado no banco."));
    }

    // 2. Salva ou atualiza um usuário (com criptografia de senha)
    @Transactional
    public Usuario salvarUsuario(Usuario usuario) {
        if (usuario.getSenha() != null && !usuario.getSenha().isEmpty()) {
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }
        if (usuario.getIdUsuario() == null && usuario.getDataCadastro() == null) {
            usuario.setDataCadastro(java.time.LocalDateTime.now());
        }
        return usuarioRepository.save(usuario);
    }

    // 3. Altera o status da conta (Ativo/Inativo) - RESOLVE O ERRO DE COMPILAÇÃO
    @Transactional
    public Usuario alterarStatusConta(Integer id, String status) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));

        usuario.setStatusConta(status);
        return usuarioRepository.save(usuario);
    }

    // 4. Métodos de busca e listagem
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

    // 5. Exclusão
    @Transactional
    public void excluirUsuario(Integer id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
        } else {
            throw new RuntimeException("Não foi possível excluir. Usuário não encontrado.");
        }
    }
}