package com.appmelinabit.controller;

import com.appmelinabit.model.Apiario;
import com.appmelinabit.service.ApiarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/gerenciar")
public class ApiarioController {

    @Autowired
    private ApiarioService apiarioService;

    // MÉTODO GET: Exibir o Formulário
    @GetMapping("/cadastro-apiarios")
    public String viewCadastroApiarios(Model model) {
        // Só cria um novo se não houver um vindo do erro (FlashAttribute)
        if (!model.containsAttribute("apiario")) {
            model.addAttribute("apiario", new Apiario());
        }
        return "cadastro-apiarios";
    }

    // MÉTODO POST: Processar e Salvar
    @PostMapping("/cadastro-apiarios")
    public String salvarApiario(@ModelAttribute("apiario") Apiario apiario,
                                RedirectAttributes attributes) {
        try {
            apiarioService.salvar(apiario);

            // MENSAGEM DE SUCESSO: O usuário verá na tela de cadastro
            attributes.addFlashAttribute("mensagemSucesso", "Apiário cadastrado com sucesso!");

            // REDIRECIONA PARA A MESMA PÁGINA (Limpa o formulário e mostra a mensagem)
            return "redirect:/gerenciar/cadastro-apiarios";

        } catch (Exception e) {
            // MENSAGEM DE ERRO
            attributes.addFlashAttribute("mensagemErro", "Erro ao salvar: " + e.getMessage());
            // DEVOLVE O OBJETO PARA O USUÁRIO NÃO PERDER O QUE DIGITOU
            attributes.addFlashAttribute("apiario", apiario);

            return "redirect:/gerenciar/cadastro-apiarios";
        }
    }
}