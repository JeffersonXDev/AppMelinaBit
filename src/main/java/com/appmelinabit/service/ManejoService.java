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

    private Usuario getUsuarioLogado() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioService.findByEmail(username);
        if (usuario == null) {
            throw new RuntimeException("Usuário não logado ou não encontrado no sistema.");
        }
        return usuario;
    }

    @Transactional
    public Manejo salvarManejo(Manejo manejo) {
        // 1. Associa o usuário logado ao Manejo
        Usuario usuarioLogado = getUsuarioLogado();
        manejo.setUsuario(usuarioLogado);

        // 2. Garante que o Apiário referenciado é válido
        if (manejo.getApiario() != null && manejo.getApiario().getIdApiario() != null) {

            // CORREÇÃO: Usamos o ID como Integer diretamente, sem .longValue()
            Integer idApiario = manejo.getApiario().getIdApiario();

            Optional<Apiario> apiarioOpt = apiarioService.findById(idApiario);

            if (apiarioOpt.isEmpty()) {
                throw new IllegalArgumentException("Apiário inválido.");
            }

            manejo.setApiario(apiarioOpt.get());
        } else {
            throw new IllegalArgumentException("O Apiário é obrigatório.");
        }

        return manejoRepository.save(manejo);
    }

    public List<Manejo> buscarManejosDoUsuarioLogado() {
        // CORREÇÃO: Buscamos o ID como Integer
        Usuario usuario = getUsuarioLogado();
        Integer idUsuario = usuario.getIdUsuario();

        // O método no Repository deve ser findByUsuario_IdUsuario
        return manejoRepository.findByUsuario_IdUsuario(idUsuario);
    }
}