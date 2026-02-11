package com.appmelinabit.controller;

import com.appmelinabit.model.Fornecedor;
import com.appmelinabit.model.Usuario;
import com.appmelinabit.service.FornecedorService;
import com.appmelinabit.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/gerenciar")
public class FornecedorController {

    @Autowired
    private FornecedorService fornecedorService;

    @Autowired
    private UsuarioService usuarioService;

    // =========================================================================
    // BLOCO 1: UTILITÁRIOS (EVITA ERROS NO THYMELEAF)
    // =========================================================================

    private void carregarAtributosComuns(Model model) {
        // Garante que a lista de segmentos SEMPRE exista para o <select> não quebrar
        if (!model.containsAttribute("segmentosDisponiveis")) {
            model.addAttribute("segmentosDisponiveis", Arrays.asList(
                    "Equipamentos", "Insumos", "Embalagens",
                    "Genética", "Medicamentos", "Outros"
            ));
        }
        // Garante que o objeto fornecedor exista para o th:object não dar erro 500
        if (!model.containsAttribute("fornecedor")) {
            model.addAttribute("fornecedor", new Fornecedor());
        }
    }

    // =========================================================================
    // BLOCO 2: CADASTRO-FORNECEDORES (PÁGINA INDIVIDUAL)
    // =========================================================================

    @GetMapping("/cadastro-fornecedores")
    public String viewCadastroFornecedor(Model model) {
        carregarAtributosComuns(model);
        return "cadastro-fornecedores";
    }

    @PostMapping("/cadastro-fornecedores")
    public String salvarFornecedor(@ModelAttribute("fornecedor") Fornecedor fornecedor,
                                   Principal principal, RedirectAttributes attributes) {
        return processarSalvamento(fornecedor, principal, attributes, "redirect:/gerenciar/cadastro-fornecedores");
    }

    // =========================================================================
    // BLOCO 3: GERENCIAR-FORNECEDORES (SPA - BUSCA E LISTA)
    // =========================================================================

    @GetMapping("/gerenciar-fornecedores")
    public String gerenciarFornecedores(@RequestParam(value = "keyword", required = false) String keyword,
                                        Model model, Principal principal) {

        if (principal == null) return "redirect:/login";

        Usuario usuarioLogado = usuarioService.findByEmail(principal.getName());
        List<Fornecedor> fornecedores;

        // Bloco de Pesquisa
        if (keyword != null && !keyword.isEmpty()) {
            fornecedores = fornecedorService.buscarPorKeyword(keyword, usuarioLogado);
        } else {
            fornecedores = fornecedorService.listarPorUsuario(usuarioLogado);
        }

        carregarAtributosComuns(model);
        model.addAttribute("fornecedores", fornecedores);
        model.addAttribute("keyword", keyword);

        return "gerenciar-fornecedores";
    }

    @PostMapping("/gerenciar-fornecedores")
    public String salvarNoGerenciamento(@ModelAttribute("fornecedor") Fornecedor fornecedor,
                                        Principal principal, RedirectAttributes attributes) {
        // Aqui ele vai processar tanto o NOVO quanto a ATUALIZAÇÃO (baseado no ID)
        return processarSalvamento(fornecedor, principal, attributes, "redirect:/gerenciar/gerenciar-fornecedores");
    }

    // =========================================================================
    // BLOCO 4: AÇÕES (EDITAR / EXCLUIR)
    // =========================================================================

    @GetMapping("/gerenciar-fornecedores/{id}")
    public String prepararEdicao(@PathVariable("id") Integer id, Model model, Principal principal) {
        try {
            Fornecedor f = fornecedorService.buscarPorId(id);
            model.addAttribute("fornecedor", f);
        } catch (Exception e) {
            model.addAttribute("mensagemErro", "Fornecedor não encontrado.");
        }
        return gerenciarFornecedores(null, model, principal);
    }

    @GetMapping("/gerenciar-fornecedores/excluir/{id}")
    public String excluirFornecedor(@PathVariable("id") Integer id, RedirectAttributes attributes) {
        try {
            fornecedorService.excluir(id);
            attributes.addFlashAttribute("mensagemSucesso", "Fornecedor excluído!");
        } catch (Exception e) {
            attributes.addFlashAttribute("mensagemErro", "Erro ao excluir.");
        }
        return "redirect:/gerenciar/gerenciar-fornecedores";
    }

    // =========================================================================
    // BLOCO 5: LÓGICA DE PERSISTÊNCIA (MÉTODO PRIVADO)
    // =========================================================================

    private String processarSalvamento(Fornecedor fornecedor, Principal principal,
                                       RedirectAttributes attributes, String redirectPath) {
        if (principal == null) return "redirect:/login";
        try {
            Usuario usuarioLogado = usuarioService.findByEmail(principal.getName());
            fornecedor.setUsuario(usuarioLogado);
            fornecedorService.salvar(fornecedor);
            attributes.addFlashAttribute("mensagemSucesso", "Operação realizada com sucesso!");
        } catch (Exception e) {
            attributes.addFlashAttribute("mensagemErro", "Erro: " + e.getMessage());
        }
        return redirectPath;
    }
}