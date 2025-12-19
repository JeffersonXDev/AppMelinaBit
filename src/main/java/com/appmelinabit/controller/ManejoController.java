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

    /**
     * Mapeia a URL /gerenciar/manejo (GET).
     * Prepara o formulário de cadastro e carrega as listas de dados.
     */
    @GetMapping
    public String gerenciarManejo(Model model) {
        
        // 1. Adiciona um novo objeto Manejo para o formulário de cadastro/edição
        // Verifica se já existe um objeto "manejo" (ex: após um erro de POST)
        if (!model.containsAttribute("manejo")) {
             model.addAttribute("manejo", new Manejo());
        }

        // 2. Adiciona a lista de Apiários do usuário logado para o dropdown do formulário
        // Usando o método do seu ApiarioService
        try {
            model.addAttribute("apiarios", apiarioService.buscarApiariosDoUsuarioLogado());
        } catch (RuntimeException e) {
            // Caso o usuário não esteja logado ou não tenha Apiários
            model.addAttribute("mensagemErro", "Erro ao carregar Apiários: " + e.getMessage());
            model.addAttribute("apiarios", java.util.Collections.emptyList());
        }
        
        // 3. Adiciona a lista de Manejos do usuário logado para a tabela de listagem
        model.addAttribute("manejos", manejoService.buscarManejosDoUsuarioLogado());

        // Retorna o nome do template Thymeleaf
        return "manejo/gerenciar-manejo"; 
    }
    
    /**
     * Processa o envio do formulário de Manejo (POST).
     * Mapeia a URL /gerenciar/manejo (POST).
     */
    @PostMapping
    public String salvarManejo(@ModelAttribute("manejo") Manejo manejo, 
                               RedirectAttributes attributes) {
        try {
            manejoService.salvarManejo(manejo);
            attributes.addFlashAttribute("mensagemSucesso", "Manejo registrado com sucesso!");
        } catch (IllegalArgumentException e) {
            // Erros de validação (ex: Apiário não pertence ao usuário, campo obrigatório faltando)
            attributes.addFlashAttribute("mensagemErro", e.getMessage());
            // Retorna o objeto Manejo para repopular o formulário com os dados
            attributes.addFlashAttribute("manejo", manejo);
            return "redirect:/gerenciar/manejo"; 
        } catch (Exception e) {
            // Outros erros
            attributes.addFlashAttribute("mensagemErro", "Ocorreu um erro interno ao salvar o manejo.");
            attributes.addFlashAttribute("manejo", manejo);
            return "redirect:/gerenciar/manejo";
        }
        
        // Redireciona para o GET, que recarrega a página com a lista atualizada
        return "redirect:/gerenciar/manejo";
    }
}