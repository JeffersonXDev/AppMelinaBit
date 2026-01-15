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

    // ==========================================
    // 1. PÁGINA DE CADASTRO (cadastro-apiarios.html)
    // ==========================================
    @GetMapping("/cadastro-apiarios")
    public String viewCadastroApiarios(Model model) {
        if (!model.containsAttribute("apiario")) {
            model.addAttribute("apiario", new Apiario());
        }
        return "cadastro-apiarios"; // Retorna o arquivo de cadastro
    }

    @PostMapping("/cadastro-apiarios")
    public String salvarNovoApiario(@ModelAttribute("apiario") Apiario apiario, RedirectAttributes attributes) {
        try {
            apiarioService.salvar(apiario);
            attributes.addFlashAttribute("mensagemSucesso", "Apiário cadastrado com sucesso!");
            return "redirect:/gerenciar/cadastro-apiarios";
        } catch (Exception e) {
            attributes.addFlashAttribute("mensagemErro", "Erro ao cadastrar: " + e.getMessage());
            attributes.addFlashAttribute("apiario", apiario);
            return "redirect:/gerenciar/cadastro-apiarios";
        }
    }

    // ==========================================
    // 2. PÁGINA DE GERENCIAMENTO (gerenciar-apiarios.html)
    // ==========================================
    @GetMapping("/gerenciar-apiarios")
    public String listarApiarios(Model model, @RequestParam(value = "keyword", required = false) String keyword) {
        if (keyword != null && !keyword.isEmpty()) {
            model.addAttribute("apiarios", apiarioService.buscarPorNomeOuCidade(keyword));
            model.addAttribute("keyword", keyword);
        } else {
            model.addAttribute("apiarios", apiarioService.listarTodos());
        }

        // Objeto necessário para o formulário de edição que fica nesta página
        if (!model.containsAttribute("apiario")) {
            model.addAttribute("apiario", new Apiario());
        }
        return "gerenciar-apiarios";
    }
    @GetMapping("/gerenciar-apiario/{id}")
    public String prepararEdicao(@PathVariable("id") Integer id, Model model) {
        Apiario apiarioExistente = apiarioService.buscarPorId(id);
        model.addAttribute("apiario", apiarioExistente); // Alimenta os campos
        model.addAttribute("apiarios", apiarioService.listarTodos()); // Mantém a lista
        return "gerenciar-apiarios";
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

    @GetMapping("/excluir-apiario/{id}")
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