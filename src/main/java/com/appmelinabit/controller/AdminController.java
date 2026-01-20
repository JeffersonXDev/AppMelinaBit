package com.appmelinabit.controller;

import com.appmelinabit.model.Usuario;
import com.appmelinabit.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal; // IMPORT NECESSÁRIO
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/dashboard")
    public String viewAdminDashboard(Model model) {
        List<Usuario> usuarios = usuarioRepository.findAll();

        // Calcula a soma de todos os 'valorRecebido'
        BigDecimal faturamentoTotal = usuarios.stream()
                .map(u -> u.getValorRecebido() != null ? u.getValorRecebido() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("usuarios", usuarios);
        model.addAttribute("totalUsuarios", usuarios.size());
        model.addAttribute("faturamentoTotal", faturamentoTotal);

        return "admin-dashboard";
    }

    @PostMapping("/usuarios/status/{id}")
    public String toggleStatus(@PathVariable Integer id) {
        Usuario user = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário inválido:" + id));

        if ("ATIVO".equalsIgnoreCase(user.getStatusConta())) {
            user.setStatusConta("BLOQUEADO");
        } else {
            user.setStatusConta("ATIVO");
        }

        usuarioRepository.save(user);
        return "redirect:/admin/dashboard";
    }
}