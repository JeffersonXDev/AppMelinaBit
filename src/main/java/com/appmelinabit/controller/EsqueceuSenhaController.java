package com.appmelinabit.controller;

import com.appmelinabit.service.SenhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
// ... (outros imports) ...

@Controller
public class EsqueceuSenhaController {

    @Autowired
    private SenhaService senhaService;

    @GetMapping("/esqueceu-senha")
    public String exibirFormulario() {
        return "esqueceu-senha"; // Nome do seu arquivo HTML
    }

    @PostMapping("/esqueceu-senha")
    public String processarEsqueciSenha(@RequestParam("email") String email, Model model) {
        
        try {

            senhaService.iniciarProcessoRedefinicao(email);

            model.addAttribute("mensagemSucesso", 
            	    "E-mail de redefinição de senha foi enviado para " + email + ". " + "<a class='/link/' href='/login'>Faça login</a>");


            return "esqueceu-senha"; 

        } catch (RuntimeException e) {

            model.addAttribute("mensagemErro", "Falha ao enviar o e-mail. Por favor, tente novamente ou contate o suporte.");
            
            return "esqueceu-senha";
        }
    }
}