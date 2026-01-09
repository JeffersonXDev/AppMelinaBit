package com.appmelinabit.controller;

import com.appmelinabit.service.SenhaService;
import jakarta.servlet.http.HttpServletRequest; // IMPORTANTE
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EsqueceuSenhaController {

    @Autowired
    private SenhaService senhaService;

    @GetMapping("/esqueceu-senha")
    public String exibirFormulario() {
        return "esqueceu-senha";
    }

    @PostMapping("/esqueceu-senha")
    public String processarEsqueciSenha(@RequestParam("email") String email,
                                        HttpServletRequest request, // ADICIONE ISSO
                                        Model model) {
        try {
            // AGORA PASSAMOS O 'request' PARA O SERVICE
            senhaService.iniciarProcessoRedefinicao(email, request);

            model.addAttribute("mensagemSucesso",
                    "E-mail de redefinição de senha foi enviado para " + email + ".");

            return "esqueceu-senha";

        } catch (RuntimeException e) {
            model.addAttribute("mensagemErro", "Falha ao enviar o e-mail: " + e.getMessage());
            return "esqueceu-senha";
        }
    }
}