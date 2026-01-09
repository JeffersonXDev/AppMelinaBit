package com.appmelinabit.service;

import com.appmelinabit.model.Apiario;
import com.appmelinabit.model.Manejo;
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
    private UsuarioService usuarioService;

    @Transactional
    public Apiario salvar(Apiario apiario) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Ajustado: UsuarioService agora retorna Optional ou Usuario direto?
        // Se o seu UsuarioService.findByEmail retorna Usuario:
        Usuario usuarioLogado = usuarioService.findByEmail(username);

        if (usuarioLogado != null) {
            apiario.setUsuario(usuarioLogado);
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

        return apiarioRepository.findByUsuario(usuarioLogado);
    }

    // CORREÇÃO AQUI: Mudado de Long para Integer
    public Optional<Apiario> findById(Integer idApiario) {
        return apiarioRepository.findById(idApiario);
    }

    public void salvarManejo(Manejo manejo) {

    }
}