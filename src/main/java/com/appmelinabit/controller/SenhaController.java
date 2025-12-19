package com.appmelinabit.controller;

import com.appmelinabit.service.SenhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SenhaController {

    @Autowired
    private SenhaService senhaService;

    @GetMapping("/redefinir-senha")
    public String exibirPaginaRedefinicao(@RequestParam("token") String token, Model model) {
        if (senhaService.getTokenValido(token).isPresent()) {
            model.addAttribute("token", token);
            return "redefinir-senha"; // Nome do arquivo HTML a ser exibido
        }
        model.addAttribute("mensagem", "Token inválido ou expirado.");
        return "erro-redefinicao"; // Você pode criar uma página de erro
    }

    @PostMapping("/redefinir-senha")
    public String redefinirSenha(@RequestParam("token") String token, 
                                 @RequestParam("novaSenha") String novaSenha,
                                 Model model) {
        if (senhaService.redefinirSenha(token, novaSenha).isPresent()) {
            model.addAttribute("mensagem", "Senha redefinida com sucesso!");
            return "sucesso-redefinicao"; // Você pode criar uma página de sucesso
        }
        model.addAttribute("mensagem", "Erro ao redefinir a senha. Tente novamente.");
        return "erro-redefinicao";
    }
}