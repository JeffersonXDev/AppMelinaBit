package com.appmelinabit.controller;

import com.appmelinabit.model.Fornecedor;
import com.appmelinabit.model.Usuario; // Assumindo que você tem uma classe Usuario
import com.appmelinabit.service.FornecedorService;
import com.appmelinabit.service.UsuarioService; // Assumindo que você tem um serviço para buscar o usuário
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal; // Para pegar o usuário logado

@Controller
@RequestMapping("/gerenciar")
public class FornecedorController {

    @Autowired
    private FornecedorService fornecedorService;

    @Autowired 
    private UsuarioService usuarioService; 

    @GetMapping("/fornecedores")
    public String viewCadastroFornecedor(Model model) {

        model.addAttribute("fornecedor", new Fornecedor()); 

        return "cadastro-fornecedores"; // Nome do template HTML
    }

    @PostMapping("/fornecedores")
    public String salvarFornecedor(
        @ModelAttribute("fornecedor") Fornecedor fornecedor, 
        Principal principal, // Injeta o usuário logado do Spring Security
        Model model, 
        RedirectAttributes attributes) {
        
        if (principal == null) {

            attributes.addFlashAttribute("erro", "Sessão expirada. Faça login novamente.");
            return "redirect:/login"; 
        }

        try {

            String emailUsuarioLogado = principal.getName();

            Usuario usuarioLogado = usuarioService.findByEmail(emailUsuarioLogado);
            
            if (usuarioLogado == null) {
                 throw new IllegalStateException("Usuário logado não encontrado no banco de dados.");
            }


            fornecedor.setUsuario(usuarioLogado);
            
            // 4. Salva o Fornecedor com a associação
            fornecedorService.salvar(fornecedor);
            
            attributes.addFlashAttribute("mensagemSucesso", "Fornecedor cadastrado com sucesso!");

            return "redirect:/gerenciar/fornecedores/lista"; 
            
        } catch (Exception e) {

            model.addAttribute("erro", "Erro ao cadastrar fornecedor: " + e.getMessage());
            model.addAttribute("fornecedor", fornecedor); // Mantém os dados preenchidos
            return "cadastro-fornecedores"; 
        }
    }
}