package com.appmelinabit.controller;

import com.appmelinabit.model.Usuario;
import com.appmelinabit.repository.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.time.LocalDate;

@Controller
public class DashboardController {

    private final MovimentacaoEstoqueRepository movimentacaoRepo;
    private final ApiarioRepository apiarioRepo;
    private final UsuarioRepository usuarioRepo;
    private final ProducaoRepository producaoRepo;

    public DashboardController(MovimentacaoEstoqueRepository movimentacaoRepo,
                               ApiarioRepository apiarioRepo,
                               UsuarioRepository usuarioRepo,
                               ProducaoRepository producaoRepo) {
        this.movimentacaoRepo = movimentacaoRepo;
        this.apiarioRepo = apiarioRepo;
        this.usuarioRepo = usuarioRepo;
        this.producaoRepo = producaoRepo;
    }

    @GetMapping("/dashboard")
    public String viewUserDashboard(Model model, @AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails == null) return "redirect:/login";

        Usuario usuario = usuarioRepo.findByEmail(userDetails.getUsername()).orElse(null);

        if (usuario != null) {
            LocalDate dataLimite = LocalDate.now().minusDays(7);

            // VENDAS (Valores em R$)
            model.addAttribute("vendaMel", nuloParaZero(movimentacaoRepo.sumVendasSemana("mel", usuario, dataLimite)));
            model.addAttribute("vendaPropolis", nuloParaZero(movimentacaoRepo.sumVendasSemana("propolis", usuario, dataLimite)));
            model.addAttribute("vendaPolen", nuloParaZero(movimentacaoRepo.sumVendasSemana("polen", usuario, dataLimite)));
            model.addAttribute("vendaCera", nuloParaZero(movimentacaoRepo.sumVendasSemana("cera", usuario, dataLimite)));

            // ESTOQUE (Conta: Produção - Venda)
            model.addAttribute("estoqueMel", calcularSaldo("mel", usuario));
            model.addAttribute("estoquePropolis", calcularSaldo("propolis", usuario));
            model.addAttribute("estoquePolen", calcularSaldo("polen", usuario));
            model.addAttribute("estoqueCera", calcularSaldo("cera", usuario));

            // 3. DADOS GERAIS
            model.addAttribute("totalApiarios", nuloParaZeroObj(apiarioRepo.countByUsuario(usuario)));
            model.addAttribute("totalColmeias", nuloParaZeroObj(apiarioRepo.sumColmeiasByUsuario(usuario)));

            model.addAttribute("usuario", usuario);
        }

        return "user-dashboard";
    }

    private BigDecimal calcularSaldo(String termoBusca, Usuario usuario) {
        // Busca a soma na tabela 'producoes' (Ex: busca 'mel' em 'Mel')
        BigDecimal totalProduzido = nuloParaZero(producaoRepo.somarProducaoPorProduto(usuario, termoBusca));

        // Busca a soma na tabela 'movimentacao_estoque' (Ex: busca 'mel' em '1kg_mel')
        BigDecimal totalVendido = nuloParaZero(movimentacaoRepo.sumTotalVendasProduto(termoBusca, usuario));

        // Subtrai: Produção - Vendas
        return totalProduzido.subtract(totalVendido);
    }

    private BigDecimal nuloParaZero(BigDecimal valor) {
        return valor != null ? valor : BigDecimal.ZERO;
    }

    private Number nuloParaZeroObj(Object valor) {
        if (valor == null) return 0;
        return (valor instanceof Number) ? (Number) valor : 0;
    }
}