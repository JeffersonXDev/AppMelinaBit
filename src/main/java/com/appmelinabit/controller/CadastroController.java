package com.appmelinabit.controller;

import com.appmelinabit.model.Usuario;
import com.appmelinabit.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // IMPORTANTE

@Controller
public class CadastroController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/cadastro")
    public String exibirPaginaCadastro(Model model) {
        // Inicializa o objeto para o Thymeleaf não dar erro de null
        if (!model.containsAttribute("usuario")) {
            model.addAttribute("usuario", new Usuario());
        }
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String salvarNovoUsuario(
            @ModelAttribute Usuario usuario,
            @RequestParam("confirmarSenha") String confirmarSenha,
            Model model,
            RedirectAttributes attributes) { // Adicionado para mensagens de sucesso

        // 1. Validação de Senha
        if (!usuario.getSenha().equals(confirmarSenha)) {
            model.addAttribute("erroSenha", "As senhas não conferem. Tente novamente.");
            model.addAttribute("usuario", usuario); // Mantém os dados preenchidos (exceto senha)
            return "cadastro";
        }

        try {
            // 2. Configurações padrão para novos usuários
            usuario.setNivel("ROLE_USER");
            usuario.setStatusConta("Inativo"); 

            // 3. Salva no banco (O Service deve cuidar do BCrypt da senha)
            usuarioService.salvarUsuario(usuario);

            // 4. MENSAGEM DE SUCESSO: Enviada via FlashAttribute para sobreviver ao redirect
            attributes.addFlashAttribute("mensagemSucesso", "Usuário cadastrado, será enviado e-mail validando o cadastro!");

            // 5. REDIRECIONAMENTO: Volta para a página de cadastro para exibir a mensagem
            return "redirect:/cadastro";

        } catch (Exception e) {
            // Caso o e-mail já exista ou ocorra erro de banco
            model.addAttribute("erroSenha", "Erro ao cadastrar: E-mail ou CPF já podem estar em uso.");
            model.addAttribute("usuario", usuario);
            return "cadastro";
        }
    }
}