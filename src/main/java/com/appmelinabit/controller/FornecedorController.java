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
    
    // Adicione a injeção do serviço de usuário
    @Autowired 
    private UsuarioService usuarioService; 

    // --- Mapeamento GET: Exibir o Formulário ---
    @GetMapping("/fornecedores")
    public String viewCadastroFornecedor(Model model) {
        
        // 1. Adiciona o objeto Fornecedor vazio para o Thymeleaf se ligar
        model.addAttribute("fornecedor", new Fornecedor()); 
        
        // O template 'cadastro-fornecedores' não precisa do objeto 'usuario' neste método, 
        // apenas o objeto 'fornecedor'.
        
        return "cadastro-fornecedores"; // Nome do template HTML
    }
    
    // --- Mapeamento POST: Processar e Salvar o Formulário ---
    @PostMapping("/fornecedores")
    public String salvarFornecedor(
        @ModelAttribute("fornecedor") Fornecedor fornecedor, 
        Principal principal, // Injeta o usuário logado do Spring Security
        Model model, 
        RedirectAttributes attributes) {
        
        if (principal == null) {
            // Se o usuário não estiver logado, redireciona para o login
            attributes.addFlashAttribute("erro", "Sessão expirada. Faça login novamente.");
            return "redirect:/login"; 
        }

        try {
            // 1. Obtém o e-mail/username do usuário logado
            String emailUsuarioLogado = principal.getName();
            
            // 2. Busca o objeto Usuario completo no banco de dados
            // Certifique-se de que este método exista no seu UserService e retorne um Usuario
            Usuario usuarioLogado = usuarioService.findByEmail(emailUsuarioLogado);
            
            if (usuarioLogado == null) {
                 throw new IllegalStateException("Usuário logado não encontrado no banco de dados.");
            }

            // 3. ASSOCIA O USUÁRIO AO FORNECEDOR (CRÍTICO devido ao nullable=false)
            // Certifique-se de que este método 'setUsuario' exista na sua classe Fornecedor.java
            fornecedor.setUsuario(usuarioLogado);
            
            // 4. Salva o Fornecedor com a associação
            fornecedorService.salvar(fornecedor);
            
            attributes.addFlashAttribute("mensagemSucesso", "Fornecedor cadastrado com sucesso!");
            
            // Redireciona para onde você lista os fornecedores
            return "redirect:/gerenciar/fornecedores/lista"; 
            
        } catch (Exception e) {
            // Em caso de erro (ex: CNPJ duplicado ou falha de banco)
            model.addAttribute("erro", "Erro ao cadastrar fornecedor: " + e.getMessage());
            model.addAttribute("fornecedor", fornecedor); // Mantém os dados preenchidos
            return "cadastro-fornecedores"; 
        }
    }
}