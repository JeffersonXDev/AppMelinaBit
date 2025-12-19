package com.appmelinabit.service;

import com.appmelinabit.model.Apiario;
import com.appmelinabit.model.Usuario; 
import com.appmelinabit.repository.ApiarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ApiarioService {

    @Autowired
    private ApiarioRepository apiarioRepository;
    
    @Autowired 
    private UsuarioService usuarioService; // Utiliza o serviço de usuário já definido

    /**
     * Salva o Apiário e anexa o usuário logado (Lógica de segurança).
     * @param apiario O objeto Apiario a ser salvo.
     * @return O Apiário salvo.
     */
    @Transactional
    public Apiario salvar(Apiario apiario) {
        
        // 1. Obtém o nome de usuário (email) do contexto de segurança
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); 
        
        // 2. Busca o objeto Usuario completo usando o UsuarioService
        Usuario usuarioLogado = usuarioService.findByEmail(username);
        
        if (usuarioLogado != null) {
            // 3. Associa o Apiário ao usuário logado
            apiario.setUsuario(usuarioLogado); // Requer o método setUsuario na Entidade Apiario
        } else {
            throw new RuntimeException("Usuário logado não encontrado no sistema.");
        }
        
        return apiarioRepository.save(apiario);
    }

    /**
     * Retorna a lista de TODOS os Apiários. 
     * Este é o método que deve ser chamado para o dropdown, assumindo que 
     * o ManejoController está chamando apiarioService.findAll().
     * @return Lista de todos os Apiários.
     */
    public List<Apiario> findAll() { 
        return apiarioRepository.findAll();
    }
    
    /**
     * Retorna a lista de Apiários cadastrados pelo usuário logado.
     * Recomendado para listagem na tela do usuário.
     * @return Lista de Apiários filtrada pelo usuário.
     */
    public List<Apiario> buscarApiariosDoUsuarioLogado() { 
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            return List.of(); 
        }

        String username = authentication.getName();
        Usuario usuarioLogado = usuarioService.findByEmail(username);

        if (usuarioLogado == null) {
            return List.of();
        }
        
        // Requer o método findByUsuario(Usuario) no ApiarioRepository
        return apiarioRepository.findByUsuario(usuarioLogado); 
    }
    
    /**
     * Busca um Apiário específico por ID.
     * @param idApiario O ID do Apiário a ser buscado.
     * @return Um Optional contendo o Apiário, se encontrado.
     */
    public Optional<Apiario> findById(Long idApiario) {
        return apiarioRepository.findById(idApiario);
    }
}