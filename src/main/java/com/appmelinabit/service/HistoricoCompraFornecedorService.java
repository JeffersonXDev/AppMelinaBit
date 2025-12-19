package com.appmelinabit.service;

import com.appmelinabit.model.HistoricoCompraFornecedor;
import com.appmelinabit.model.Usuario;
import com.appmelinabit.repository.HistoricoCompraFornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class HistoricoCompraFornecedorService {

    @Autowired
    private HistoricoCompraFornecedorRepository historicoRepository;
    
    @Autowired 
    private UsuarioService usuarioService; 
    
    // Você precisará do FornecedorService para buscar o objeto Fornecedor
    @Autowired 
    private FornecedorService fornecedorService; 
    
    // Você pode precisar do ProdutoService, dependendo da sua lógica

    /**
     * Salva o registro de compra e anexa o usuário logado.
     */
    public HistoricoCompraFornecedor salvar(HistoricoCompraFornecedor historico) {
        
        // 1. **Obter e Anexar o Usuário Logado**
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName(); 
            
            Usuario usuarioLogado = usuarioService.findByEmail(username);
            
            // Note: Não precisamos anexar o Usuario ao HistoricoCompraFornecedor 
            // diretamente se o Fornecedor já estiver ligado ao Usuario. 
            // No entanto, se quisermos rastrear quem FEZ a compra, o campo 
            // id_usuario deveria estar mapeado na Entidade Histórico.

            // ⚠️ Se você adicionou id_usuario ao HistoricoCompraFornecedor, use a linha abaixo:
            // historico.setUsuario(usuarioLogado); 

        } catch (Exception e) {
            throw new RuntimeException("Falha na autenticação ao tentar salvar o histórico de compra.", e);
        }
        
        // 2. **Processar Chaves Estrangeiras (Fornecedor e Produto)**
        // Se o seu formulário envia apenas o ID do fornecedor, você deve buscá-lo aqui 
        // para garantir que o objeto Fornecedor completo esteja anexado ao Histórico.
        /*
        if (historico.getFornecedor() != null && historico.getFornecedor().getId() != null) {
            Fornecedor f = fornecedorService.findById(historico.getFornecedor().getId());
            historico.setFornecedor(f);
        }
        */
        
        return historicoRepository.save(historico);
    }
}