package com.appmelinabit.service;

import com.appmelinabit.model.Usuario;
import com.appmelinabit.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Transactional
    public Usuario salvarUsuario(Usuario usuario) {
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


    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }
    
    @Transactional
    public Usuario alterarStatusConta(Long id, String status) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));
        
        // Assumimos que a entidade Usuario tem um campo 'statusConta' com o método setStatusConta(String)
        usuario.setStatusConta(status); 
        
        return usuarioRepository.save(usuario);
    }

	public void excluirUsuario(Long id) {
		// TODO Auto-generated method stub
		
	}
}