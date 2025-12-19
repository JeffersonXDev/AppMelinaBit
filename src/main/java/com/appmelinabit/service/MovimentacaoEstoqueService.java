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
    
    // Serviço para buscar o objeto Usuario completo
    @Autowired 
    private UsuarioService usuarioService; 

    @Transactional // Garante que tudo seja salvo ou nada seja salvo (atomicidade)
    public MovimentacaoEstoque salvar(MovimentacaoEstoque movimentacao) {
        
        // **1. Obter e Anexar o Usuário Logado (Segurança)**
        anexarUsuarioLogado(movimentacao);

        // **2. Validação e Regras de Negócio**
        // Este é o local ideal para adicionar lógicas como:
        // - Validar se id_cliente não é nulo se tipo_movimentacao for 'SAIDA'.
        // - Validar se id_fornecedor não é nulo se tipo_movimentacao for 'ENTRADA'.
        // - Calcular preço base ou custo se estiverem vazios.
        
        // Exemplo de Validação Simples:
        if (movimentacao.getNome() == null || movimentacao.getNome().trim().isEmpty()) {
             throw new IllegalArgumentException("O nome do produto é obrigatório.");
        }
        
        // **3. Persistir no Banco de Dados**
        return movimentacaoEstoqueRepository.save(movimentacao);
    }

    /**
     * Extrai e anexa o objeto Usuario completo à MovimentacaoEstoque.
     * @param movimentacao O objeto MovimentacaoEstoque a ser atualizado.
     */
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
		// TODO Auto-generated method stub
		
	}
}