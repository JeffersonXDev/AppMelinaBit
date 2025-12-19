package com.appmelinabit.controller;

import com.appmelinabit.model.Apiario;
import com.appmelinabit.service.ApiarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/gerenciar")
public class ApiarioController {

    @Autowired
    private ApiarioService apiarioService; 

    // MÉTODO GET: Exibir o Formulário
    @GetMapping("/cadastro-apiarios")
    public String viewCadastroApiarios(Model model) {
        model.addAttribute("apiario", new Apiario()); 
        return "cadastro-apiarios"; 
    }
    
    // MÉTODO POST: Processar e Salvar o Formulário (Mantendo o POST na mesma URL)
    @PostMapping("/cadastro-apiarios") 
    public String salvarApiario(@ModelAttribute("apiario") Apiario apiario, Model model) {
        
        try {
            apiarioService.salvar(apiario);
            return "redirect:/dashboard?success=ApiarioSalvo"; 
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao salvar o apiário: " + e.getMessage());
            model.addAttribute("apiario", apiario); 
            return "cadastro-apiarios"; 
        }
    }
}