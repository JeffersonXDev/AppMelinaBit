package com.appmelinabit.controller;

import com.appmelinabit.model.Fornecedor;
import com.appmelinabit.model.Usuario;
import com.appmelinabit.service.FornecedorService;
import com.appmelinabit.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/gerenciar")
public class FornecedorController {

    @Autowired
    private FornecedorService fornecedorService;

    @Autowired
    private UsuarioService usuarioService;

    // AJUSTE: Mapeado para /gerenciar/cadastro-fornecedores conforme o Dashboard
    @GetMapping("/cadastro-fornecedores")
    public String viewCadastroFornecedor(Model model) {
        model.addAttribute("fornecedor", new Fornecedor());
        return "cadastro-fornecedores";
    }

    // AJUSTE: Mantendo a mesma rota para o POST
    @PostMapping("/cadastro-fornecedores")
    public String salvarFornecedor(
            @ModelAttribute("fornecedor") Fornecedor fornecedor,
            Principal principal,
            RedirectAttributes attributes) { // Removi o 'Model model' pois usaremos apenas redirect

        if (principal == null) {
            return "redirect:/login";
        }

        try {
            String emailUsuarioLogado = principal.getName();
            Usuario usuarioLogado = usuarioService.findByEmail(emailUsuarioLogado);

            if (usuarioLogado == null) {
                throw new IllegalStateException("Usuário logado não encontrado.");
            }

            fornecedor.setUsuario(usuarioLogado);
            fornecedorService.salvar(fornecedor);

            // Mensagem que aparecerá após o redirecionamento (Formulário Limpo)
            attributes.addFlashAttribute("mensagemSucesso", "Fornecedor cadastrado com sucesso!");

            return "redirect:/gerenciar/cadastro-fornecedores";

        } catch (Exception e) {
            // Para erro, também usamos RedirectAttributes para evitar o reenvio do formulário (F5)
            attributes.addFlashAttribute("mensagemErro", "Erro ao cadastrar fornecedor: " + e.getMessage());
            return "redirect:/gerenciar/cadastro-fornecedores";
        }
    }
}