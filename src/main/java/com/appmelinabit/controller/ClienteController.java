package com.appmelinabit.controller;

import com.appmelinabit.model.Cliente;
import com.appmelinabit.model.Usuario;
import com.appmelinabit.repository.UsuarioRepository;
import com.appmelinabit.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/gerenciar")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/cadastro-clientes")
    public String viewCadastroClientes(Model model) {
        // Se não houver um cliente (vindo de um erro de validação), cria um novo
        if (!model.containsAttribute("cliente")) {
            model.addAttribute("cliente", new Cliente());
        }
        return "cadastro-clientes";
    }

    @PostMapping("/cadastro-clientes")
    public String salvarCliente(@ModelAttribute("cliente") Cliente cliente,
                                RedirectAttributes attributes) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = usuarioRepository.findByEmail(auth.getName()).orElseThrow();

            cliente.setUsuario(usuario);
            clienteService.salvar(cliente);

            // Adiciona a mensagem que sobrevive ao redirecionamento
            attributes.addFlashAttribute("mensagemSucesso", "Cliente cadastrado com sucesso!");

            // REDIRECIONA PARA A ROTA CORRETA (com /gerenciar)
            return "redirect:/gerenciar/cadastro-clientes";

        } catch (Exception e) {
            attributes.addFlashAttribute("mensagemErro", "Erro ao salvar o cliente: " + e.getMessage());
            attributes.addFlashAttribute("cliente", cliente);
            return "redirect:/gerenciar/cadastro-clientes";
        }
    }
}