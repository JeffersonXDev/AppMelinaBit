package com.appmelinabit.controller;

import com.appmelinabit.model.Usuario;
import com.appmelinabit.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // <--- NOVO IMPORT
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam; // <--- NOVO IMPORT

@Controller
public class CadastroController {
    
    @Autowired
    private UsuarioService usuarioService;

    // Garante que o objeto Usuario esteja no Model para o formulário
    @GetMapping("/cadastro")
    public String exibirPaginaCadastro(Model model) {
        // Se você usar th:object="${usuario}" no HTML, precisa adicionar isso
        if (!model.containsAttribute("usuario")) {
            model.addAttribute("usuario", new Usuario());
        }
        return "cadastro";
    }

    // --- O PROCESSO DE VALIDAÇÃO ESTÁ AQUI ---
    @PostMapping("/cadastro")
    public String salvarNovoUsuario(
            @ModelAttribute Usuario usuario,
            @RequestParam("confirmarSenha") String confirmarSenha, // <--- RECEBE O CAMPO EXTRA
            Model model) {
        
        // 1. VALIDAÇÃO DE SENHA NO SERVIDOR
        if (!usuario.getSenha().equals(confirmarSenha)) {
            // Adiciona a mensagem de erro ao Model
            model.addAttribute("erroSenha", "A senha e a confirmação de senha não conferem.");
            
            // Retorna o objeto Usuario de volta para pré-preencher o formulário
            model.addAttribute("usuario", usuario);
            
            // Retorna para a página de cadastro para que o usuário veja o erro
            return "cadastro"; 
        }

        // 2. PROCESSO DE SALVAR (Se a validação acima passar)
        usuario.setNivel("USUARIO_COMUM");
        
        // Se a senha for criptografada no Service, ela deve ser feita após esta validação
        usuarioService.salvarUsuario(usuario); 
        
        return "redirect:/login"; 
    }
}