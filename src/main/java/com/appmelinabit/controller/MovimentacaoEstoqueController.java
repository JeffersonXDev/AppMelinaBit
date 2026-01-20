package com.appmelinabit.controller;

import com.appmelinabit.model.*;
import com.appmelinabit.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/gerenciar")
public class MovimentacaoEstoqueController {

    @Autowired private MovimentacaoEstoqueRepository movimentacaoEstoqueRepository;
    @Autowired private ClienteRepository clienteRepository;
    @Autowired private FornecedorRepository fornecedorRepository;
    @Autowired private ApiarioRepository apiarioRepository;
    @Autowired private UsuarioRepository usuarioRepository;

    private Usuario buscarUsuarioLogado(UserDetails userDetails) {
        return usuarioRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    // =========================================================================
    // BLOCO 1: VENDAS (SAÍDA)
    // =========================================================================

    @GetMapping("/cadastro-vendas")
    public String abrirCadastroVendas(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Usuario usuario = buscarUsuarioLogado(userDetails);
        MovimentacaoEstoque mov = new MovimentacaoEstoque();
        mov.setTipoMovimentacao("SAIDA");
        model.addAttribute("movimentacao", mov);
        model.addAttribute("clientes", clienteRepository.findByUsuario(usuario));
        return "cadastro-vendas";
    }

    @GetMapping("/gerenciar-vendas")
    public String gerenciarVendas(@RequestParam(value = "keyword", required = false) String keyword,
                                  Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Usuario usuario = buscarUsuarioLogado(userDetails);
        List<MovimentacaoEstoque> lista = (keyword != null && !keyword.isEmpty())
                ? movimentacaoEstoqueRepository.buscarTudo(keyword, usuario)
                : movimentacaoEstoqueRepository.findByUsuarioOrderByDataEntradaDesc(usuario);

        model.addAttribute("vendas", lista.stream().filter(m -> "SAIDA".equals(m.getTipoMovimentacao())).toList());
        model.addAttribute("clientes", clienteRepository.findByUsuario(usuario));
        model.addAttribute("keyword", keyword);

        if (!model.containsAttribute("movimentacao")) {
            MovimentacaoEstoque mov = new MovimentacaoEstoque();
            mov.setTipoMovimentacao("SAIDA");
            model.addAttribute("movimentacao", mov);
        }
        return "gerenciar-vendas";
    }

    @GetMapping("/gerenciar-vendas/editar/{id}")
    public String editarVenda(@PathVariable("id") Integer id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        MovimentacaoEstoque mov = movimentacaoEstoqueRepository.findById(id).orElseThrow();
        model.addAttribute("movimentacao", mov);
        return gerenciarVendas(null, model, userDetails);
    }

    // =========================================================================
    // BLOCO 2: PRODUÇÃO (COLHEITA)
    // =========================================================================

    @GetMapping("/cadastro-producao")
    public String abrirCadastroProducao(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Usuario usuario = buscarUsuarioLogado(userDetails);
        MovimentacaoEstoque mov = new MovimentacaoEstoque();
        mov.setTipoMovimentacao("COLHEITA");
        model.addAttribute("movimentacao", mov);
        model.addAttribute("apiarios", apiarioRepository.findByUsuario(usuario));
        return "cadastro-producao";
    }

    @GetMapping("/gerenciar-producao")
    public String gerenciarProducao(@RequestParam(value = "keyword", required = false) String keyword,
                                    Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Usuario usuario = buscarUsuarioLogado(userDetails);
        List<MovimentacaoEstoque> lista = (keyword != null && !keyword.isEmpty())
                ? movimentacaoEstoqueRepository.buscarTudo(keyword, usuario)
                : movimentacaoEstoqueRepository.findByUsuarioOrderByDataEntradaDesc(usuario);

        model.addAttribute("movimentacoes", lista.stream().filter(m -> "COLHEITA".equals(m.getTipoMovimentacao())).toList());
        model.addAttribute("apiarios", apiarioRepository.findByUsuario(usuario));

        if (!model.containsAttribute("movimentacao")) {
            MovimentacaoEstoque mov = new MovimentacaoEstoque();
            mov.setTipoMovimentacao("COLHEITA");
            model.addAttribute("movimentacao", mov);
        }
        return "gerenciar-producao";
    }

    @GetMapping("/gerenciar-producao/editar/{id}")
    public String editarProducao(@PathVariable("id") Integer id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        MovimentacaoEstoque mov = movimentacaoEstoqueRepository.findById(id).orElseThrow();
        model.addAttribute("movimentacao", mov);
        return gerenciarProducao(null, model, userDetails);
    }

    // =========================================================================
    // BLOCO 3: INSUMOS (ENTRADA)
    // =========================================================================

    @GetMapping("/cadastro-insumos")
    public String abrirCadastroInsumos(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Usuario usuario = buscarUsuarioLogado(userDetails);
        MovimentacaoEstoque mov = new MovimentacaoEstoque();
        mov.setTipoMovimentacao("ENTRADA");
        model.addAttribute("movimentacao", mov);
        model.addAttribute("fornecedores", fornecedorRepository.findByUsuario(usuario));
        return "cadastro-insumos";
    }

    @GetMapping("/gerenciar-insumos")
    public String gerenciarInsumos(@RequestParam(value = "keyword", required = false) String keyword,
                                   Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Usuario usuario = buscarUsuarioLogado(userDetails);
        List<MovimentacaoEstoque> lista = (keyword != null && !keyword.isEmpty())
                ? movimentacaoEstoqueRepository.buscarTudo(keyword, usuario)
                : movimentacaoEstoqueRepository.findByUsuarioOrderByDataEntradaDesc(usuario);

        model.addAttribute("insumos", lista.stream().filter(m -> "ENTRADA".equals(m.getTipoMovimentacao())).toList());
        model.addAttribute("fornecedores", fornecedorRepository.findByUsuario(usuario));

        if (!model.containsAttribute("movimentacao")) {
            MovimentacaoEstoque mov = new MovimentacaoEstoque();
            mov.setTipoMovimentacao("ENTRADA");
            model.addAttribute("movimentacao", mov);
        }
        return "gerenciar-insumos";
    }

    @GetMapping("/gerenciar-insumos/editar/{id}")
    public String editarInsumo(@PathVariable("id") Integer id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        MovimentacaoEstoque mov = movimentacaoEstoqueRepository.findById(id).orElseThrow();
        model.addAttribute("movimentacao", mov);
        return gerenciarInsumos(null, model, userDetails);
    }

    // =========================================================================
    // BLOCO 4: PROCESSAMENTO (Salvar e Excluir)
    // =========================================================================

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("movimentacao") MovimentacaoEstoque mov,
                         @AuthenticationPrincipal UserDetails userDetails,
                         RedirectAttributes attributes) {
        try {
            Usuario usuario = buscarUsuarioLogado(userDetails);
            mov.setUsuario(usuario);
            mov.setIdUsuario(usuario.getIdUsuario());
            if (mov.getDataCadastro() == null) mov.setDataCadastro(LocalDate.now());

            movimentacaoEstoqueRepository.save(mov);
            attributes.addFlashAttribute("mensagemSucesso", "Registro salvo com sucesso!");
        } catch (Exception e) {
            attributes.addFlashAttribute("mensagemErro", "Erro ao salvar: " + e.getMessage());
        }

        if ("ENTRADA".equals(mov.getTipoMovimentacao())) return "redirect:/gerenciar/gerenciar-insumos";
        if ("SAIDA".equals(mov.getTipoMovimentacao())) return "redirect:/gerenciar/gerenciar-vendas";
        return "redirect:/gerenciar/gerenciar-producao";
    }

    @GetMapping("/excluir-estoque/{id}")
    public String excluir(@PathVariable("id") Integer id, RedirectAttributes attributes) {
        MovimentacaoEstoque mov = movimentacaoEstoqueRepository.findById(id).orElse(null);
        String redirect = "/gerenciar/gerenciar-producao";

        if (mov != null) {
            if ("ENTRADA".equals(mov.getTipoMovimentacao())) redirect = "/gerenciar/gerenciar-insumos";
            if ("SAIDA".equals(mov.getTipoMovimentacao())) redirect = "/gerenciar/gerenciar-vendas";

            movimentacaoEstoqueRepository.deleteById(id);
            attributes.addFlashAttribute("mensagemSucesso", "Excluído com sucesso!");
        }
        return "redirect:" + redirect;
    }
}