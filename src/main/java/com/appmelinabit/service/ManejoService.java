package com.appmelinabit.service;

import com.appmelinabit.model.Apiario;
import com.appmelinabit.model.Manejo;
import com.appmelinabit.model.Usuario;
import com.appmelinabit.repository.ManejoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ManejoService {

    @Autowired
    private ManejoRepository manejoRepository;
    
    @Autowired
    private ApiarioService apiarioService; 
    
    @Autowired
    private UsuarioService usuarioService; 

    /**
     * Obtém o objeto Usuário completo do usuário logado.
     */
    private Usuario getUsuarioLogado() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioService.findByEmail(username);
        if (usuario == null) {
            throw new RuntimeException("Usuário não logado ou não encontrado no sistema.");
        }
        return usuario;
    }

    /**
     * Salva ou atualiza um registro de Manejo.
     * Resolve o erro de Type Mismatch convertendo Integer para Long.
     */
    @Transactional
    public Manejo salvarManejo(Manejo manejo) {
        
        // 1. Associa o usuário logado ao Manejo
        Usuario usuarioLogado = getUsuarioLogado();
        manejo.setUsuario(usuarioLogado);
        
        // 2. Garante que o Apiário referenciado é válido e pertence ao usuário
        if (manejo.getApiario() != null && manejo.getApiario().getIdApiario() != null) {
            
            // Convertendo explicitamente Integer para Long para a busca no serviço de Apiário
            Long idApiario = manejo.getApiario().getIdApiario().longValue();
            
            Optional<Apiario> apiarioOpt = apiarioService.findById(idApiario);
            
            if (apiarioOpt.isEmpty()) {
                throw new IllegalArgumentException("Apiário inválido ou você não tem permissão para usá-lo.");
            }
            
            manejo.setApiario(apiarioOpt.get()); 
        } else {
             throw new IllegalArgumentException("O Apiário é obrigatório para o registro de manejo.");
        }
        
        // 3. Persiste a entidade completa
        return manejoRepository.save(manejo);
    }
    
    /**
     * Lista todos os registros de Manejo do usuário logado.
     * Resolve o erro de Type Mismatch convertendo o ID do Usuário para Long.
     */
    public List<Manejo> buscarManejosDoUsuarioLogado() {
        // 🎯 SOLUÇÃO DO ERRO: Caso getIdUsuario() retorne Integer no seu modelo Usuario,
        // forçamos a conversão para Long para satisfazer o ManejoRepository.
        Usuario usuario = getUsuarioLogado();
        Long idUsuario = usuario.getIdUsuario().longValue();
        
        return manejoRepository.findByUsuarioIdUsuario(idUsuario);
    }
}