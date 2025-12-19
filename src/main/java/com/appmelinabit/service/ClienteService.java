package com.appmelinabit.service;

import com.appmelinabit.model.Cliente;
import com.appmelinabit.model.Usuario;
import com.appmelinabit.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List; // ⬅️ Não se esqueça deste import

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired 
    private UsuarioService usuarioService; 

    public Cliente salvar(Cliente cliente) {
        
        // Lógica para obter e anexar o Usuario logado (mantida)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); 
        
        Usuario usuarioLogado = usuarioService.findByEmail(username);
        
        if (usuarioLogado != null) {
            // Requer o método setUsuario na Entidade Cliente
            cliente.setUsuario(usuarioLogado);
        } else {
            throw new RuntimeException("Usuário logado não encontrado no sistema.");
        }
        
        return clienteRepository.save(cliente);
    }

    /**
     * Retorna a lista de Clientes cadastrados pelo usuário logado.
     * Método essencial para o Controller de Movimentação.
     */
    public List<Cliente> buscarTodos() { // ⬅️ Implementação corrigida
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            // Retorna lista vazia se não houver usuário logado
            return List.of(); 
        }

        String username = authentication.getName();
        Usuario usuarioLogado = usuarioService.findByEmail(username);

        if (usuarioLogado == null) {
            return List.of();
        }
        
        // ** Requer o método findByUsuario(Usuario) no ClienteRepository **
        return clienteRepository.findByUsuario(usuarioLogado); 
    }
}