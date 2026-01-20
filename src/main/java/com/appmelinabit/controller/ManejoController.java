package com.appmelinabit.controller;

import com.appmelinabit.model.Manejo;
import com.appmelinabit.model.Usuario;
import com.appmelinabit.service.ManejoService;
import com.appmelinabit.service.ApiarioService;
import com.appmelinabit.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/gerenciar")
public class ManejoController {

    @Autowired
    private ManejoService manejoService;

    @Autowired
    private ApiarioService apiarioService;

    @Autowired
    private UsuarioService usuarioService;

    // --- CADASTRO (Mantido) ---
    @GetMapping("/cadastro-manejos")
    public String exibirCadastroManejo(Model model) {
        if (!model.containsAttribute("manejo")) {
            model.addAttribute("manejo", new Manejo());
        }
        model.addAttribute("apiarios", apiarioService.buscarApiariosDoUsuarioLogado());
        return "cadastro-manejos";
    }

    // --- GERENCIAMENTO (CRUD) ---
    @GetMapping("/gerenciar-manejos")
    public String gerenciarManejos(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        Usuario usuario = usuarioService.buscarUsuarioLogado();

        List<Manejo> lista = (keyword != null && !keyword.isEmpty())
                ? manejoService.pesquisarManejos(keyword, usuario)
                : manejoService.listarManejosPorUsuario(usuario);

        model.addAttribute("manejos", lista);
        model.addAttribute("keyword", keyword);

        // ADIÇÃO IMPORTANTE: Carrega apiários para o formulário de edição/cadastro na mesma tela
        model.addAttribute("apiarios", apiarioService.buscarApiariosDoUsuarioLogado());

        if (!model.containsAttribute("manejo")) {
            model.addAttribute("manejo", new Manejo());
        }
        return "gerenciar-manejos";
    }

    // --- EDIÇÃO (Atualizado para carregar tudo) ---
    @GetMapping("/gerenciar-manejos/editar/{id}")
    public String editarManejo(@PathVariable("id") Integer id, Model model) {
        Manejo manejo = manejoService.buscarPorId(id);
        model.addAttribute("manejo", manejo);
        // Ao retornar o gerenciarManejos, ele agora levará a lista de apiários e os dados do manejo
        return gerenciarManejos(null, model);
    }

    // --- SALVAR (Sua lógica de redirecionamento mantida) ---
    @PostMapping("/salvar-manejo")
    public String salvarManejo(@ModelAttribute("manejo") Manejo manejo, RedirectAttributes attributes) {
        try {
            manejoService.salvarManejo(manejo);
            attributes.addFlashAttribute("mensagemSucesso", "Registro de manejo salvo com sucesso!");
        } catch (Exception e) {
            attributes.addFlashAttribute("mensagemErro", "Erro ao processar: " + e.getMessage());
        }

        // Se o ID já existia (Edição), volta para o gerenciar. Se era novo, volta para o cadastro.
        return (manejo.getIdManejo() != null)
                ? "redirect:/gerenciar/gerenciar-manejos"
                : "redirect:/gerenciar/cadastro-manejos";
    }

    // --- EXCLUIR (Mantido) ---
    @GetMapping("/excluir-manejo/{id}")
    public String excluirManejo(@PathVariable("id") Integer id, RedirectAttributes attributes) {
        try {
            manejoService.excluirManejo(id);
            attributes.addFlashAttribute("mensagemSucesso", "Registro removido!");
        } catch (Exception e) {
            attributes.addFlashAttribute("mensagemErro", "Erro ao excluir.");
        }
        return "redirect:/gerenciar/gerenciar-manejos";
    }
}