package com.appmelinabit.controller;

import com.appmelinabit.model.Usuario;
import com.appmelinabit.repository.UsuarioRepository;
import com.appmelinabit.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage; // Adicionado
import org.springframework.mail.javamail.JavaMailSender; // Adicionado
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JavaMailSender mailSender; // Injetado para o e-mail

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public String viewAdminDashboard(Model model) {
        // MUDANÇA: Busca todos, mas filtra para NÃO mostrar o e-mail do admin na tabela
        List<Usuario> usuarios = usuarioRepository.findAll().stream()
                .filter(u -> !u.getEmail().equalsIgnoreCase("melinabit.suporte@gmail.com"))
                .collect(Collectors.toList());

        BigDecimal faturamentoTotal = usuarios.stream()
                .map(u -> u.getValorRecebido() != null ? u.getValorRecebido() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("usuarios", usuarios);
        model.addAttribute("totalUsuarios", usuarios.size());
        model.addAttribute("faturamentoTotal", faturamentoTotal);

        return "admin-dashboard";
    }

    @PostMapping("/status/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String alternarStatus(@PathVariable Integer id, org.springframework.web.servlet.mvc.support.RedirectAttributes attributes) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        String novoStatus = "ATIVO".equals(usuario.getStatusConta()) ? "INATIVO" : "ATIVO";
        usuarioService.alterarStatusConta(id, novoStatus);

        // Envio de e-mail (sua lógica)
        if ("ATIVO".equals(novoStatus)) {
            enviarEmailAtivacao(usuario.getEmail(), usuario.getNome());
        }

        // A MENSAGEM: Usamos FlashAttribute para ela "sobreviver" ao redirect
        String textoAcao = "ATIVO".equals(novoStatus) ? "ativado" : "bloqueado";
        attributes.addFlashAttribute("mensagem", "O usuário " + usuario.getNome() + " foi " + textoAcao + " com sucesso!");

        // IMPORTANTE: Isso garante que você continue no Dashboard
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/excluir/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String excluirUsuario(@PathVariable Integer id) {
        try {
            usuarioRepository.deleteById(id);
        } catch (Exception e) {
            System.err.println("Erro ao excluir: " + e.getMessage());
            return "redirect:/admin/dashboard?error=true";
        }
        return "redirect:/admin/dashboard";
    }

    // Método auxiliar para o envio
    private void enviarEmailAtivacao(String emailDestino, String nome) {
        try {
            SimpleMailMessage mensagem = new SimpleMailMessage();
            mensagem.setTo(emailDestino);
            mensagem.setSubject("Conta Ativada - AppMelinaBit");
            mensagem.setText("Olá " + nome + ",\n\nSua conta foi ativada pelo administrador. " +
                    "Agora você já pode acessar o sistema!\n\n" +
                    "Acesse: http://localhost:8080/login");
            mailSender.send(mensagem);
        } catch (Exception e) {
            System.err.println("Erro ao enviar e-mail: " + e.getMessage());
        }
    }
}