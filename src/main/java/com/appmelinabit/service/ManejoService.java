package com.appmelinabit.service;

import com.appmelinabit.model.*;
import com.appmelinabit.repository.ManejoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ManejoService {

    @Autowired private ManejoRepository manejoRepository;
    @Autowired private ApiarioService apiarioService;
    @Autowired private UsuarioService usuarioService;

    private Usuario getUsuarioLogado() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioService.findByEmail(username);
        if (usuario == null) throw new RuntimeException("Usuário não encontrado.");
        return usuario;
    }

    // Corrigido para retornar a lista exigida pelo Controller
    public List<Manejo> listarManejosPorUsuario(Usuario usuario) {
        return manejoRepository.findByUsuarioOrderByDataInspecaoDesc(usuario);
    }

    // Corrigido: Agora tem retorno em todos os caminhos (if/else)
    public List<Manejo> pesquisarManejos(String keyword, Usuario usuario) {
        if (keyword != null && !keyword.isEmpty()) {
            return manejoRepository.buscarManejos(keyword, usuario);
        }
        return listarManejosPorUsuario(usuario);
    }

    public Manejo buscarPorId(Integer id) {
        return manejoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manejo não encontrado"));
    }

    @Transactional
    public Manejo salvarManejo(Manejo manejo) {
        Usuario usuarioLogado = getUsuarioLogado();
        manejo.setUsuario(usuarioLogado);

        if (manejo.getApiario() == null || manejo.getApiario().getIdApiario() == null) {
            throw new IllegalArgumentException("O Apiário é obrigatório.");
        }

        return manejoRepository.save(manejo); // Retorno obrigatório aqui
    }

    @Transactional
    public void excluirManejo(Integer id) {
        manejoRepository.deleteById(id);
    }

    public List<Manejo> buscarManejosDoUsuarioLogado() {
        Usuario usuario = getUsuarioLogado();
        return manejoRepository.findByUsuario_IdUsuario(usuario.getIdUsuario());
    }
}