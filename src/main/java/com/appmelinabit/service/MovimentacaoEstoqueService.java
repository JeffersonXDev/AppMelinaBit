package com.appmelinabit.service;

import com.appmelinabit.model.MovimentacaoEstoque;
import com.appmelinabit.model.Usuario;
import com.appmelinabit.repository.MovimentacaoEstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importe para garantir atomicidade

@Service
public class MovimentacaoEstoqueService {

    @Autowired
    private MovimentacaoEstoqueRepository movimentacaoEstoqueRepository;

    @Autowired 
    private UsuarioService usuarioService; 

    @Transactional
    public MovimentacaoEstoque salvar(MovimentacaoEstoque movimentacao) {
        

        anexarUsuarioLogado(movimentacao);

        

        if (movimentacao.getNome() == null || movimentacao.getNome().trim().isEmpty()) {
             throw new IllegalArgumentException("O nome do produto é obrigatório.");
        }
        

        return movimentacaoEstoqueRepository.save(movimentacao);
    }


    private void anexarUsuarioLogado(MovimentacaoEstoque movimentacao) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            // Lança uma exceção de segurança se o usuário não estiver autenticado
            throw new SecurityException("Acesso negado: Usuário não autenticado.");
        }
        
        String username = authentication.getName(); 
        
        // Busca o objeto Usuario completo
        Usuario usuarioLogado = usuarioService.findByEmail(username);
        
        if (usuarioLogado != null) {
            movimentacao.setUsuario(usuarioLogado);
        } else {
            // Isso pode ocorrer se o usuário foi excluído recentemente, mas a sessão ainda existe
            throw new RuntimeException("Usuário logado (" + username + ") não encontrado no sistema.");
        }
    }

	public void registrarVenda(MovimentacaoEstoque movimentacao) {

		
	}
}