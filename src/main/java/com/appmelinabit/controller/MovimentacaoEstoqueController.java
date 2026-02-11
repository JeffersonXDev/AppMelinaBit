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

    @Autowired
    private MovimentacaoEstoqueRepository movimentacaoEstoqueRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private FornecedorRepository fornecedorRepository;
    @Autowired
    private ApiarioRepository apiarioRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

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

    // =========================================================================
    // BLOCO 2: PRODUÇÃO (AGORA É ENTRADA)
    // =========================================================================

    @GetMapping("/cadastro-producao")
    public String abrirCadastroProducao(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Usuario usuario = buscarUsuarioLogado(userDetails);
        MovimentacaoEstoque mov = new MovimentacaoEstoque();
        mov.setTipoMovimentacao("ENTRADA");
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

        // Filtra o que é ENTRADA e veio de um Apiário (Produção)
        model.addAttribute("movimentacoes", lista.stream()
                .filter(m -> "ENTRADA".equals(m.getTipoMovimentacao()) && m.getApiario() != null).toList());
        model.addAttribute("apiarios", apiarioRepository.findByUsuario(usuario));

        if (!model.containsAttribute("movimentacao")) {
            MovimentacaoEstoque mov = new MovimentacaoEstoque();
            mov.setTipoMovimentacao("ENTRADA");
            model.addAttribute("movimentacao", mov);
        }
        return "gerenciar-producao";
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

        // Filtra o que é ENTRADA e veio de Fornecedor (Insumos)
        model.addAttribute("insumos", lista.stream()
                .filter(m -> "ENTRADA".equals(m.getTipoMovimentacao()) && m.getFornecedor() != null).toList());
        model.addAttribute("fornecedores", fornecedorRepository.findByUsuario(usuario));

        if (!model.containsAttribute("movimentacao")) {
            MovimentacaoEstoque mov = new MovimentacaoEstoque();
            mov.setTipoMovimentacao("ENTRADA");
            model.addAttribute("movimentacao", mov);
        }
        return "gerenciar-insumos";
    }
    // =========================================================================
    // BLOCO 4: PROCESSAMENTO (Salvar e Excluir) corrigido para as 6 páginas
    // =========================================================================

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("movimentacao") MovimentacaoEstoque mov,
                         @RequestParam(value = "origem", required = false) String origem,
                         @AuthenticationPrincipal UserDetails userDetails,
                         RedirectAttributes attributes) {
        try {
            // 1. Limpeza de String Duplicada (Ex: "SAIDA,SAIDA" vira apenas "SAIDA")
            if (mov.getTipoMovimentacao() != null && mov.getTipoMovimentacao().contains(",")) {
                mov.setTipoMovimentacao(mov.getTipoMovimentacao().split(",")[0]);
            }

            Usuario usuario = buscarUsuarioLogado(userDetails);
            mov.setUsuario(usuario);
            mov.setIdUsuario(usuario.getIdUsuario());

            if (mov.getDataCadastro() == null) {
                mov.setDataCadastro(LocalDate.now());
            }

            movimentacaoEstoqueRepository.save(mov);
            attributes.addFlashAttribute("mensagemSucesso", "Registro salvo com sucesso!");

        } catch (Exception e) {
            attributes.addFlashAttribute("mensagemErro", "Erro ao salvar: " + e.getMessage());
            // Em caso de erro, tenta voltar para a origem ou usa a lógica de segurança
            return (origem != null) ? "redirect:/gerenciar/" + origem : redirecionarSeguranca(mov);
        }

        // 2. RETORNO DINÂMICO PARA AS 6 PÁGINAS
        // Se o seu HTML enviou o input name="origem", o Java volta para ela exatamente.
        if (origem != null && !origem.isEmpty()) {
            return "redirect:/gerenciar/" + origem;
        }

        // 3. LOGICA DE SEGURANÇA (Se a origem falhar)
        return redirecionarSeguranca(mov);
    }

    // Método de segurança para não dar erro se o parâmetro origem sumir
    private String redirecionarSeguranca(MovimentacaoEstoque mov) {
        if ("SAIDA".equals(mov.getTipoMovimentacao())) return "redirect:/gerenciar/cadastro-vendas";
        if (mov.getApiario() != null) return "redirect:/gerenciar/cadastro-producao";
        return "redirect:/gerenciar/cadastro-insumos";
    }

    // --- ROTAS DE EDIÇÃO AJUSTADAS ---

    // =========================================================================
    // BLOCO 5: EXCLUSÃO
    // =========================================================================

    @GetMapping("/gerenciar-vendas/{id}") // Para o botão "Corrigir"
    public String carregarVenda(@PathVariable("id") Integer id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("movimentacao", movimentacaoEstoqueRepository.findById(id).orElseThrow());
        return gerenciarVendas(null, model, userDetails);
    }

    @GetMapping("/gerenciar-vendas/excluir/{id}") // Para o botão "Excluir"
    public String excluirVenda(@PathVariable("id") Integer id, RedirectAttributes attributes) {
        movimentacaoEstoqueRepository.deleteById(id);
        attributes.addFlashAttribute("mensagemSucesso", "Venda excluída!");
        return "redirect:/gerenciar/gerenciar-vendas";
    }

    // === CORREÇÃO PARA PRODUÇÃO ===
    @GetMapping("/gerenciar-producao/{id}")
    public String carregarProducao(@PathVariable("id") Integer id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("movimentacao", movimentacaoEstoqueRepository.findById(id).orElseThrow());
        return gerenciarProducao(null, model, userDetails);
    }

    @GetMapping("/gerenciar-producao/excluir/{id}")
    public String excluirProducao(@PathVariable("id") Integer id, RedirectAttributes attributes) {
        movimentacaoEstoqueRepository.deleteById(id);
        attributes.addFlashAttribute("mensagemSucesso", "Produção excluída!");
        return "redirect:/gerenciar/gerenciar-producao";
    }

    // Para a página de Insumos
    @GetMapping("/gerenciar-insumos/{id}")
    public String carregarInsumo(@PathVariable("id") Integer id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("movimentacao", movimentacaoEstoqueRepository.findById(id).orElseThrow());
        return gerenciarInsumos(null, model, userDetails);
    }

    // Rota para o botão "Excluir" (Insumos) - Corrigindo o 404
    @GetMapping("/gerenciar-insumos/excluir/{id}")
    public String excluirInsumo(@PathVariable("id") Integer id, RedirectAttributes attributes) {
        try {
            movimentacaoEstoqueRepository.deleteById(id);
            attributes.addFlashAttribute("mensagemSucesso", "Insumo excluído com sucesso!");
        } catch (Exception e) {
            attributes.addFlashAttribute("mensagemErro", "Erro ao excluir: " + e.getMessage());
        }
        return "redirect:/gerenciar/gerenciar-insumos";
    }
}