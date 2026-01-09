package com.appmelinabit.controller;

import com.appmelinabit.model.Manejo;
import com.appmelinabit.service.ManejoService;
import com.appmelinabit.service.ApiarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/gerenciar") // Padronizado com os outros
public class ManejoController {

    @Autowired
    private ManejoService manejoService;

    @Autowired
    private ApiarioService apiarioService;

    // AJUSTE: Rota exata do botão do Dashboard
    @GetMapping("/cadastro-manejos")
    public String exibirCadastroManejo(Model model) {
        if (!model.containsAttribute("manejo")) {
            model.addAttribute("manejo", new Manejo());
        }

        try {
            // Carrega os apiários para o <select> do formulário
            model.addAttribute("apiarios", apiarioService.buscarApiariosDoUsuarioLogado());
        } catch (Exception e) {
            model.addAttribute("mensagemErro", "Erro ao carregar Apiários.");
            model.addAttribute("apiarios", java.util.Collections.emptyList());
        }

        return "cadastro-manejos"; // Nome do arquivo .html na pasta templates
    }

    // AJUSTE: POST na mesma rota para evitar confusão
    @PostMapping("/cadastro-manejos")
    public String salvarManejo(@ModelAttribute("manejo") Manejo manejo,
                               RedirectAttributes attributes) {
        try {
            manejoService.salvarManejo(manejo);
            attributes.addFlashAttribute("mensagemSucesso", "Manejo registrado com sucesso!");
            return "redirect:/gerenciar/cadastro-manejos";
        } catch (Exception e) {
            attributes.addFlashAttribute("mensagemErro", "Erro ao salvar o Manejo, tente novamente: " + e.getMessage());
            attributes.addFlashAttribute("manejo", manejo);
            return "redirect:/gerenciar/cadastro-manejos";
        }
    }
}