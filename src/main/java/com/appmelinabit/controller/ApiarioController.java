package com.appmelinabit.controller;

import com.appmelinabit.model.Apiario;
import com.appmelinabit.service.ApiarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/gerenciar")
public class ApiarioController {

    @Autowired
    private ApiarioService apiarioService;

    @GetMapping("/cadastro-apiarios")
    public String viewCadastroApiarios(Model model) {
        if (!model.containsAttribute("apiario")) {
            model.addAttribute("apiario", new Apiario());
        }
        return "cadastro-apiarios";
    }

    @PostMapping("/cadastro-apiarios")
    public String salvarNovoApiario(@ModelAttribute("apiario") Apiario apiario, RedirectAttributes attributes) {
        try {
            apiarioService.salvar(apiario);
            attributes.addFlashAttribute("mensagemSucesso", "Apiário cadastrado com sucesso!");
        } catch (Exception e) {
            attributes.addFlashAttribute("mensagemErro", "Erro ao cadastrar: " + e.getMessage());
            attributes.addFlashAttribute("apiario", apiario);
        }
        return "redirect:/gerenciar/cadastro-apiarios";
    }

    @GetMapping("/gerenciar-apiarios")
    public String listarApiarios(Model model, @RequestParam(value = "keyword", required = false) String keyword) {
        List<Apiario> lista = (keyword != null && !keyword.isEmpty())
                ? apiarioService.buscarPorNomeOuCidade(keyword)
                : apiarioService.buscarApiariosDoUsuarioLogado();

        model.addAttribute("apiarios", lista);

        if (!model.containsAttribute("apiario")) {
            model.addAttribute("apiario", new Apiario());
        }
        return "gerenciar-apiarios";
    }

    // === NOVA ROTA: CORRIGE O ERRO 404 AO CLICAR EM CORRIGIR ===
    @GetMapping("/gerenciar-apiarios/{id}")
    public String carregarApiario(@PathVariable("id") Integer id, Model model) {
        Apiario api = apiarioService.buscarPorId(id);
        model.addAttribute("apiario", api);
        return listarApiarios(model, null); // Reutiliza a listagem para manter a tabela na tela
    }

    @PostMapping("/gerenciar-apiarios")
    public String atualizarApiario(@ModelAttribute("apiario") Apiario apiario, RedirectAttributes attributes) {
        try {
            apiarioService.salvar(apiario);
            attributes.addFlashAttribute("mensagemSucesso", "Apiário atualizado com sucesso!");
        } catch (Exception e) {
            attributes.addFlashAttribute("mensagemErro", "Erro ao atualizar: " + e.getMessage());
        }
        return "redirect:/gerenciar/gerenciar-apiarios";
    }

    // === ROTA DE EXCLUSÃO AJUSTADA PARA O PADRÃO DO SEU HTML ===
    @GetMapping("/gerenciar-apiarios/excluir/{id}")
    public String excluirApiario(@PathVariable("id") Integer id, RedirectAttributes attributes) {
        try {
            apiarioService.excluir(id);
            attributes.addFlashAttribute("mensagemSucesso", "Apiário excluído!");
        } catch (Exception e) {
            attributes.addFlashAttribute("mensagemErro", "Erro ao excluir!");
        }
        return "redirect:/gerenciar/gerenciar-apiarios";
    }
}