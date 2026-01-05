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

    public List<Apiario> findAll() { 
        return apiarioRepository.findAll();
    }
    

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

    public Optional<Apiario> findById(Long idApiario) {
        return apiarioRepository.findById(idApiario);
    }
}