package com.appmelinabit.controller;

import com.appmelinabit.model.Manejo;
import com.appmelinabit.service.ManejoService;
import com.appmelinabit.service.ApiarioService; // Service que lista os apiários
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/gerenciar/manejo")
public class ManejoController {

    @Autowired
    private ManejoService manejoService;

    @Autowired
    private ApiarioService apiarioService;

    @GetMapping
    public String gerenciarManejo(Model model) {

        if (!model.containsAttribute("manejo")) {
             model.addAttribute("manejo", new Manejo());
        }

        try {
            model.addAttribute("apiarios", apiarioService.buscarApiariosDoUsuarioLogado());
        } catch (RuntimeException e) {
            // Caso o usuário não esteja logado ou não tenha Apiários
            model.addAttribute("mensagemErro", "Erro ao carregar Apiários: " + e.getMessage());
            model.addAttribute("apiarios", java.util.Collections.emptyList());
        }

        model.addAttribute("manejos", manejoService.buscarManejosDoUsuarioLogado());

        return "manejo/gerenciar-manejo"; 
    }
    

    @PostMapping
    public String salvarManejo(@ModelAttribute("manejo") Manejo manejo, 
                               RedirectAttributes attributes) {
        try {
            manejoService.salvarManejo(manejo);
            attributes.addFlashAttribute("mensagemSucesso", "Manejo registrado com sucesso!");
        } catch (IllegalArgumentException e) {

            attributes.addFlashAttribute("mensagemErro", e.getMessage());

            attributes.addFlashAttribute("manejo", manejo);
            return "redirect:/gerenciar/manejo"; 
        } catch (Exception e) {

            attributes.addFlashAttribute("mensagemErro", "Ocorreu um erro interno ao salvar o manejo.");
            attributes.addFlashAttribute("manejo", manejo);
            return "redirect:/gerenciar/manejo";
        }
            return "redirect:/gerenciar/manejo";
    }
}