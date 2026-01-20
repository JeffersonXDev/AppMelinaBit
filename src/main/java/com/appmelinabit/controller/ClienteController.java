package com.appmelinabit.controller;

import com.appmelinabit.model.Cliente;
import com.appmelinabit.model.Usuario;
import com.appmelinabit.repository.UsuarioRepository;
import com.appmelinabit.repository.ClienteRepository;
import com.appmelinabit.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/gerenciar")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // --- 1. ROTA DE CADASTRO (Página Limpa) ---
    @GetMapping("/cadastro-clientes")
    public String viewCadastroClientes(Model model) {
        if (!model.containsAttribute("cliente")) {
            model.addAttribute("cliente", new Cliente());
        }
        return "cadastro-clientes";
    }

    @PostMapping("/cadastro-clientes")
    public String salvarViaCadastro(@ModelAttribute("cliente") Cliente cliente, RedirectAttributes attributes) {
        processarSalvamento(cliente);
        attributes.addFlashAttribute("mensagemSucesso", "Cliente cadastrado     !");
        return "redirect:/gerenciar/cadastro-clientes";
    }

    // --- 2. ROTA DE GERENCIAMENTO (Pesquisa Geral + Tabela) ---
    @GetMapping("/gerenciar-clientes")
    public String viewGerenciarClientes(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        Usuario usuario = getUsuarioLogado();
        List<Cliente> clientes;

        if (keyword != null && !keyword.isEmpty()) {
            clientes = clienteRepository.buscarGeral(keyword, usuario);
            if (clientes.size() == 1 && !model.containsAttribute("cliente")) {
                model.addAttribute("cliente", clientes.get(0));
            }
        } else {
            clientes = clienteRepository.findByUsuario(usuario);
        }

        if (!model.containsAttribute("cliente")) {
            model.addAttribute("cliente", new Cliente());
        }

        model.addAttribute("clientes", clientes);
        model.addAttribute("keyword", keyword);
        return "gerenciar-clientes";
    }

    @PostMapping("/gerenciar-clientes")
    public String salvarViaGerenciamento(@ModelAttribute("cliente") Cliente cliente, RedirectAttributes attributes) {
        processarSalvamento(cliente);
        attributes.addFlashAttribute("mensagemSucesso", "Dados do cliente atualizados!");
        return "redirect:/gerenciar/gerenciar-clientes";
    }

    // --- MÉTODOS AUXILIARES (Para não repetir código) ---

    private void processarSalvamento(Cliente cliente) {
        Usuario usuario = getUsuarioLogado();
        cliente.setUsuario(usuario);
        clienteService.salvar(cliente);
    }

    private Usuario getUsuarioLogado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return usuarioRepository.findByEmail(auth.getName()).orElseThrow();
    }

    @GetMapping("/gerenciar-clientes/editar/{id}")
    public String editarCliente(@PathVariable Integer id, RedirectAttributes attributes) {
        Cliente cliente = clienteRepository.findById(id).orElse(new Cliente());
        attributes.addFlashAttribute("cliente", cliente);
        return "redirect:/gerenciar/gerenciar-clientes";
    }
    @GetMapping("/gerenciar-clientes/excluir/{id}")
    public String excluirCliente(@PathVariable Integer id, RedirectAttributes attributes) {
        try {
            clienteRepository.deleteById(id);
            attributes.addFlashAttribute("mensagemSucesso", "Cliente excluído com sucesso!");
        } catch (Exception e) {
            attributes.addFlashAttribute("mensagemErro", "Erro ao excluir: Este cliente possui movimentações vinculadas.");
        }
        return "redirect:/gerenciar/gerenciar-clientes";
    }
}