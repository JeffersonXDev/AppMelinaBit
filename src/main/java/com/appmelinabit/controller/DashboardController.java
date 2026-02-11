package com.appmelinabit.controller;

import com.appmelinabit.model.MovimentacaoEstoque;
import com.appmelinabit.model.Usuario;
import com.appmelinabit.repository.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class DashboardController {

    private final MovimentacaoEstoqueRepository movimentacaoRepo;
    private final ApiarioRepository apiarioRepo;
    private final UsuarioRepository usuarioRepo;

    public DashboardController(MovimentacaoEstoqueRepository movimentacaoRepo,
                               ApiarioRepository apiarioRepo,
                               UsuarioRepository usuarioRepo) {
        this.movimentacaoRepo = movimentacaoRepo;
        this.apiarioRepo = apiarioRepo;
        this.usuarioRepo = usuarioRepo;
    }

    @GetMapping("/dashboard")
    public String viewUserDashboard(Model model, @AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails == null) return "redirect:/login";

        Usuario usuario = usuarioRepo.findByEmail(userDetails.getUsername()).orElse(null);

        if (usuario != null) {
            LocalDate dataLimite = LocalDate.now().minusDays(7);

            // 1. VENDAS (Corrigido para bater com os values do Formulário: Mel, Propolis, etc.)
            model.addAttribute("vendaMel", nuloParaZero(movimentacaoRepo.sumVendasSemana("Mel", usuario, dataLimite)));
            model.addAttribute("vendaPropolis", nuloParaZero(movimentacaoRepo.sumVendasSemana("Propolis", usuario, dataLimite)));
            model.addAttribute("vendaPolen", nuloParaZero(movimentacaoRepo.sumVendasSemana("Polen", usuario, dataLimite)));
            model.addAttribute("vendaCera", nuloParaZero(movimentacaoRepo.sumVendasSemana("Cera", usuario, dataLimite)));

            // 2. ESTOQUE ATUAL (Corrigido para bater com os values do Formulário: Mel, Propolis, etc.)
            model.addAttribute("estoqueMel", nuloParaZero(movimentacaoRepo.calcularEstoquePorProduto("Mel", usuario)));
            model.addAttribute("estoquePropolis", nuloParaZero(movimentacaoRepo.calcularEstoquePorProduto("Propolis", usuario)));
            model.addAttribute("estoquePolen", nuloParaZero(movimentacaoRepo.calcularEstoquePorProduto("Polen", usuario)));
            model.addAttribute("estoqueCera", nuloParaZero(movimentacaoRepo.calcularEstoquePorProduto("Cera", usuario)));

            // 3. DADOS GERAIS
            model.addAttribute("totalApiarios", nuloParaZeroObj(apiarioRepo.countByUsuario(usuario)));
            model.addAttribute("totalColmeias", nuloParaZeroObj(apiarioRepo.sumColmeiasByUsuario(usuario)));

            // 4. LISTA DE ÚLTIMAS COLHEITAS
            List<MovimentacaoEstoque> ultimasColheitas = movimentacaoRepo.findByUsuarioOrderByDataEntradaDesc(usuario)
                    .stream()
                    .filter(m -> "COLHEITA".equalsIgnoreCase(m.getTipoMovimentacao()))
                    .limit(5)
                    .collect(Collectors.toList());

            model.addAttribute("ultimasColheitas", ultimasColheitas);
            model.addAttribute("usuario", usuario);
        }

        return "user-dashboard";
    }

    private BigDecimal nuloParaZero(BigDecimal valor) {
        return valor != null ? valor : BigDecimal.ZERO;
    }

    private Number nuloParaZeroObj(Object valor) {
        if (valor == null) return 0;
        return (valor instanceof Number) ? (Number) valor : 0;
    }
}