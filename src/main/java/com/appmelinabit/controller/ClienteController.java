package com.appmelinabit.controller;
import com.appmelinabit.model.Apiario;
import com.appmelinabit.model.Cliente;
import com.appmelinabit.service.ApiarioService;
import com.appmelinabit.service.ClienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/gerenciar")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // MÉTODO GET: Exibir o Formulário (Mapeia para /gerenciar/clientes)
    @GetMapping("/clientes")
    public String viewCadastroClientes(Model model) {
        model.addAttribute("cliente", new Cliente()); 
        return "cadastro-clientes"; // Nome do template
    }
    
    // MÉTODO POST: Processar e Salvar o Formulário
    @PostMapping("/clientes")
    public String salvarCliente(@ModelAttribute("cliente") Cliente cliente, Model model) {
        
        try {
            clienteService.salvar(cliente);
            // Redireciona para o dashboard ou lista de clientes
            return "redirect:/dashboard?success=ClienteSalvo"; 
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao salvar o cliente: " + e.getMessage());
            model.addAttribute("cliente", cliente); 
            return "cadastro-clientes"; 
        }
    }
}