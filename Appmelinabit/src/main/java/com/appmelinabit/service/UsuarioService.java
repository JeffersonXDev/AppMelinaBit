package com.appmelinabit.service;
import com.appmelinabit.model.Usuario;
import com.appmelinabit.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario salvarUsuario(Usuario usuario) {
        // Criptografa a senha antes de salvar no banco de dados
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario alterarStatusConta(Long id, String novoStatus) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);

        if (usuarioOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado com o ID: " + id);
        }

        Usuario usuario = usuarioOpt.get();
        usuario.setStatusConta(novoStatus);
        
        return usuarioRepository.save(usuario);
    }

    public void excluirUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado com o ID: " + id);
        }

        usuarioRepository.deleteById(id);
    }
}