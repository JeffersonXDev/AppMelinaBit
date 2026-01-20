package com.appmelinabit.service;

import com.appmelinabit.model.Fornecedor;
import com.appmelinabit.model.Usuario;
import com.appmelinabit.repository.FornecedorRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FornecedorService {

    private final FornecedorRepository fornecedorRepository;
    private final UsuarioService usuarioService;

    public FornecedorService(FornecedorRepository fornecedorRepository, UsuarioService usuarioService) {
        this.fornecedorRepository = fornecedorRepository;
        this.usuarioService = usuarioService;
    }

    // =========================================================================
    // BLOCO 1: PERSISTÊNCIA E SEGURANÇA
    // =========================================================================

    public Fornecedor salvar(Fornecedor fornecedor) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            throw new IllegalStateException("Operação negada: Nenhum usuário autenticado encontrado.");
        }

        String username = authentication.getName();
        Usuario usuarioLogado = usuarioService.findByEmail(username);

        if (usuarioLogado != null) {
            fornecedor.setUsuario(usuarioLogado);
        } else {
            throw new RuntimeException("Usuário logado não encontrado.");
        }

        return fornecedorRepository.save(fornecedor);
    }

    // =========================================================================
    // BLOCO 2: CONSULTAS (LISTAGEM E BUSCA POR KEYWORD)
    // =========================================================================

    public List<Fornecedor> listarPorUsuario(Usuario usuario) {
        return fornecedorRepository.findByUsuario(usuario);
    }

    public List<Fornecedor> buscarPorKeyword(String keyword, Usuario usuario) {
        // Requer o método buscarFornecedores com @Query no Repository
        return fornecedorRepository.buscarFornecedores(keyword, usuario);
    }

    public Fornecedor buscarPorId(Integer id) {
        return fornecedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fornecedor não encontrado com o ID: " + id));
    }

    // =========================================================================
    // BLOCO 3: EXCLUSÃO
    // =========================================================================

    public void excluir(Integer id) {
        if (!fornecedorRepository.existsById(id)) {
            throw new RuntimeException("Não é possível excluir: Fornecedor inexistente.");
        }
        fornecedorRepository.deleteById(id);
    }
}