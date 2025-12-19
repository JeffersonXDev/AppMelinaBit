package com.appmelinabit.controller;

import com.appmelinabit.model.MovimentacaoEstoque;
import com.appmelinabit.model.Usuario;
// ... (outros imports omitidos por brevidade)
import com.appmelinabit.repository.MovimentacaoEstoqueRepository;
import com.appmelinabit.repository.ClienteRepository;
import com.appmelinabit.repository.ApiarioRepository;
import com.appmelinabit.repository.UsuarioRepository;
import com.appmelinabit.repository.FornecedorRepository; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/gerenciar")
public class MovimentacaoEstoqueController {

    // --- 1. INJEÇÕES DE DEPENDÊNCIA ---
    @Autowired private MovimentacaoEstoqueRepository movimentacaoEstoqueRepository;
    @Autowired private ClienteRepository clienteRepository;
    @Autowired private ApiarioRepository apiarioRepository;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private FornecedorRepository fornecedorRepository;
    
    // =========================================================
    //                       MÉTODOS DE VENDA (SAÍDA)
    // =========================================================

    /**
     * Lida com a requisição GET para exibir o formulário de Venda.
     */
    @GetMapping("/venda")
    public String exibirFormularioVenda(Model model) {
        model.addAttribute("movimentacao", new MovimentacaoEstoque());
        model.addAttribute("clientes", clienteRepository.findAll());
        model.addAttribute("apiarios", apiarioRepository.findAll());
        return "cadastro-venda";
    }

    @PostMapping("/venda")
    public String cadastrarVenda(
        MovimentacaoEstoque movimentacao,
        @RequestParam("valorVendaUnitario") BigDecimal valorVendaUnitario,
        @RequestParam(value = "valorFrete", required = false, defaultValue = "0.00") BigDecimal valorFrete) 
    {
        movimentacao.setDataSaida(LocalDate.now());
        BigDecimal quantidade = new BigDecimal(movimentacao.getQuantidade());

        BigDecimal valorVendaSubtotal = valorVendaUnitario.multiply(quantidade);
        BigDecimal valorVendaTotal = valorVendaSubtotal.add(valorFrete);
        
        movimentacao.setValorVenda(valorVendaTotal);
        movimentacao.setValorFrete(valorFrete); 
        
        movimentacao.setDataCompra(null);
        movimentacao.setFornecedor(null);
        movimentacao.setValorCusto(null);

        configurarUsuarioLogado(movimentacao);

        if (movimentacao.getCliente() != null && movimentacao.getCliente().getId() != null) {
            clienteRepository.findById(movimentacao.getCliente().getId())
                .ifPresent(movimentacao::setCliente);
        }
        if (movimentacao.getApiario() != null && movimentacao.getApiario().getId() != null) {
             // CORREÇÃO DE TIPO: Converte o ID do Apiário (que pode vir como Integer) para Long, 
             // pois o findById do repositório ApiarioRepository espera Long.
             apiarioRepository.findById(movimentacao.getApiario().getId().longValue())
                .ifPresent(movimentacao::setApiario);
        }

        movimentacaoEstoqueRepository.save(movimentacao);

        return "redirect:/dashboard";
    }

    // =========================================================
    //                  MÉTODOS DE COMPRA (ENTRADA)
    // =========================================================

    /**
     * Lida com a requisição GET para exibir o formulário de Entrada.
     */
    @GetMapping("/entrada")
    public String exibirFormularioEntrada(Model model) {
        model.addAttribute("movimentacao", new MovimentacaoEstoque());
        model.addAttribute("fornecedores", fornecedorRepository.findAll());
        model.addAttribute("apiarios", apiarioRepository.findAll());
        return "cadastro-compra";
    }

    @PostMapping("/entrada")
    public String cadastrarCompra(
        MovimentacaoEstoque movimentacao,
        @RequestParam("valorCustoUnitario") BigDecimal valorCustoUnitario,
        @RequestParam(value = "valorFrete", required = false, defaultValue = "0.00") BigDecimal valorFrete) 
    {
        movimentacao.setDataCompra(LocalDate.now());
        
        movimentacao.setValorFrete(valorFrete); 
        
        BigDecimal quantidade = new BigDecimal(movimentacao.getQuantidade());
        BigDecimal valorCustoSubtotal = valorCustoUnitario.multiply(quantidade);
        BigDecimal valorCustoTotal = valorCustoSubtotal.add(valorFrete);
        
        movimentacao.setValorCusto(valorCustoTotal);

        movimentacao.setDataSaida(null);
        movimentacao.setCliente(null);
        movimentacao.setValorVenda(null);

        configurarUsuarioLogado(movimentacao);

        if (movimentacao.getFornecedor() != null && movimentacao.getFornecedor().getId() != null) {
            fornecedorRepository.findById(movimentacao.getFornecedor().getId())
                .ifPresent(movimentacao::setFornecedor);
        }
        if (movimentacao.getApiario() != null && movimentacao.getApiario().getId() != null) {
             // CORREÇÃO DE TIPO: Converte o ID do Apiário (que pode vir como Integer) para Long, 
             // pois o findById do repositório ApiarioRepository espera Long.
             apiarioRepository.findById(movimentacao.getApiario().getId().longValue())
                .ifPresent(movimentacao::setApiario);
        }

        movimentacaoEstoqueRepository.save(movimentacao);

        return "redirect:/dashboard";
    }
    
    // Método auxiliar para configurar o usuário logado
    private void configurarUsuarioLogado(MovimentacaoEstoque movimentacao) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String principalName = authentication.getName();

            Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(principalName);

            usuarioOptional.ifPresent(movimentacao::setUsuario);
        }
    }
}