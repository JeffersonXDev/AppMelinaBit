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

    /**
     * Salva ou atualiza um objeto Usuário no banco de dados.
     * @param usuario O objeto Usuário a ser salvo.
     * @return O objeto Usuário salvo.
     */
    @Transactional
    public Usuario salvarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    /**
     * Busca um Usuário pelo seu endereço de e-mail.
     * Essencial para a lógica de segurança e associação de dados.
     * @param email O e-mail do usuário.
     * @return O objeto Usuário, ou null se não for encontrado.
     */
    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email).orElse(null);
    }
    
    /**
     * Lista todos os Usuários cadastrados.
     * @return Lista de todos os Usuários.
     */
    public List<Usuario> listarTodosUsuarios() { 
        return usuarioRepository.findAll();
    }

    /**
     * Método alternativo para listar todos os Usuários cadastrados (mantido por convenção).
     * @return Lista de todos os Usuários.
     */
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    /**
     * Busca um Usuário por ID.
     * @param id O ID do usuário.
     * @return Um Optional contendo o Usuário, se encontrado.
     */
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    /**
     * Altera o status da conta de um usuário específico.
     * ESTE MÉTODO RESOLVE O ERRO: Implementa alterarStatusConta(Long, String)
     * @param id O ID do usuário cuja conta será alterada.
     * @param status O novo status da conta (ex: "ATIVO", "BLOQUEADO", "PENDENTE").
     * @throws RuntimeException se o usuário não for encontrado.
     * @return O objeto Usuário atualizado.
     */
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