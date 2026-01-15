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

    // Adicionado para resolver o erro do Controller
    public List<Apiario> listarTodos() {
        return apiarioRepository.findAll();
    }

    // Adicionado para resolver o erro de pesquisa do Controller
    public List<Apiario> buscarPorNomeOuCidade(String keyword) {
        return apiarioRepository.findByNomeContainingIgnoreCaseOrCidadeContainingIgnoreCase(keyword, keyword);
    }

    @Transactional
    public Apiario salvar(Apiario apiario) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Usuario usuarioLogado = usuarioService.findByEmail(username);

        if (usuarioLogado != null) {
            apiario.setUsuario(usuarioLogado);
        } else {
            throw new RuntimeException("Usuário logado não encontrado no sistema.");
        }

        return apiarioRepository.save(apiario);
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

    public Optional<Apiario> findById(Integer idApiario) {
        return apiarioRepository.findById(idApiario);
    }

    // Método para busca direta (utilizado no Editar do Controller)
    public Apiario buscarPorId(Integer id) {
        return apiarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Apiário não encontrado com o ID: " + id));
    }

    @Transactional
    public void excluir(Integer id) {
        apiarioRepository.deleteById(id);
    }

    public void salvarManejo(Manejo manejo) {
        // Implementação futura
    }
}