package com.appmelinabit.service;
import com.appmelinabit.model.Fornecedor;
import com.appmelinabit.model.Usuario;
import com.appmelinabit.repository.FornecedorRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder; // Import Correto
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FornecedorService {

    private final FornecedorRepository fornecedorRepository;
    private final UsuarioService usuarioService;

    // Injeção de Dependência por Construtor
    public FornecedorService(FornecedorRepository fornecedorRepository, UsuarioService usuarioService) {
        this.fornecedorRepository = fornecedorRepository;
        this.usuarioService = usuarioService;
    }

    /**
     * Salva o Fornecedor e anexa o usuário logado (segurança).
     */
    public Fornecedor salvar(Fornecedor fornecedor) {
        // 🛠️ CORREÇÃO 1: Adicionar .getAuthentication() para obter o objeto Authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); 

        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            throw new IllegalStateException("Operação negada: Nenhum usuário autenticado encontrado.");
        }
        
        String username = authentication.getName(); 
        Usuario usuarioLogado = usuarioService.findByEmail(username);
        
        if (usuarioLogado != null) {
            // Requer o método setUsuario na Entidade Fornecedor
            fornecedor.setUsuario(usuarioLogado); 
        } else {
            throw new RuntimeException("Usuário logado (" + username + ") não encontrado no sistema de usuários.");
        }
        
        return fornecedorRepository.save(fornecedor);
    }

    public List<Fornecedor> buscarTodos() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        // Verifica autenticação
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            return List.of(); 
        }

        String username = authentication.getName();
        Usuario usuarioLogado = usuarioService.findByEmail(username);

        if (usuarioLogado == null) {
            return List.of();
        }
        
        // Requer o método findByUsuario(Usuario) no FornecedorRepository
        return fornecedorRepository.findByUsuario(usuarioLogado); 
    }
}