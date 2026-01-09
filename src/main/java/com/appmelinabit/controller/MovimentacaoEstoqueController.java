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
    private ProducaoRepository producaoRepository;
    @Autowired
    private MovimentacaoEstoqueRepository movimentacaoEstoqueRepository;
    @Autowired
    private MaterialApicolaRepository materialRepo;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private FornecedorRepository fornecedorRepository;
    @Autowired
    private ApiarioRepository apiarioRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    // --- MÉTODO AUXILIAR (Fora dos outros métodos) ---
    private Usuario buscarUsuarioLogado(UserDetails userDetails) {
        return usuarioRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    // --- 1. CADASTRO DE PRODUTOS (PRODUÇÃO PRÓPRIA) ---

    @GetMapping("/cadastro-produtos")
    public String exibirCadastroProdutos(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Usuario usuario = buscarUsuarioLogado(userDetails);

        if (!model.containsAttribute("producao")) {
            model.addAttribute("producao", new Producao());
        }

        // Nome da lista deve ser 'apiarios' para bater com o th:each acima
        model.addAttribute("apiarios", apiarioRepository.findByUsuario(usuario));

        return "cadastro-produtos";
    }

    @PostMapping("/salvar-producao")
    public String salvarProducao(@ModelAttribute("producao") Producao producao,
                                 @AuthenticationPrincipal UserDetails userDetails,
                                 RedirectAttributes attributes) {
        try {
            Usuario usuario = buscarUsuarioLogado(userDetails);
            producao.setUsuario(usuario);

            // 1. SALVA NA TABELA PRODUÇÃO (Onde você vê o detalhe por apiário)
            producaoRepository.save(producao);

            // 2. SALVA NA TABELA MOVIMENTAÇÃO (Onde o Dashboard lê o estoque disponível)
            MovimentacaoEstoque mov = new MovimentacaoEstoque();
            mov.setUsuario(usuario);
            mov.setApiario(producao.getApiario());
            mov.setNome(producao.getTipoProduto());
            mov.setQuantidade(producao.getQuantidade().intValue());
            mov.setDataCompra(producao.getDataColheita()); // Data que entra no gráfico
            mov.setTipoMovimentacao("ENTRADA");

            movimentacaoEstoqueRepository.save(mov);

            attributes.addFlashAttribute("mensagemSucesso", "Produção e estoque registrados!");
        } catch (Exception e) {
            attributes.addFlashAttribute("mensagemErro", "Erro ao salvar: " + e.getMessage());
        }
        return "redirect:/gerenciar/cadastro-produtos";
    }

    // --- 2. CADASTRO DE COMPRAS (ALMOXARIFADO/INSUMOS) ---

    @GetMapping("/cadastro-compras")
    public String abrirCadastro(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Usuario usuario = buscarUsuarioLogado(userDetails);

        MaterialApicola material = new MaterialApicola();
        material.setTipoMovimentacao("ENTRADA");
        material.setQuantidadeEmUso(0.0);

        model.addAttribute("material", material);
        model.addAttribute("fornecedores", fornecedorRepository.findByUsuario(usuario));

        return "cadastro-compras";
    }

    @PostMapping("/salvar-material")
    public String salvarMaterial(@ModelAttribute("material") MaterialApicola material,
                                 @AuthenticationPrincipal UserDetails userDetails,
                                 RedirectAttributes attributes) {
        try {
            Usuario usuario = buscarUsuarioLogado(userDetails);
            material.setUsuario(usuario);

            if (material.getQuantidadeEmUso() == null) material.setQuantidadeEmUso(0.0);

            materialRepo.save(material);
            attributes.addFlashAttribute("mensagemSucesso", "Material registrado com sucesso!");
        } catch (Exception e) {
            attributes.addFlashAttribute("mensagemErro", "Erro: " + e.getMessage());
        }
        return "redirect:/gerenciar/cadastro-compras";
    }

    // --- 3. CADASTRO DE VENDAS (SAÍDA DE ESTOQUE) ---

    @GetMapping("/cadastro-vendas")
    public String exibirFormularioVenda(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Usuario usuario = buscarUsuarioLogado(userDetails);

        if (!model.containsAttribute("movimentacao")) {
            MovimentacaoEstoque mov = new MovimentacaoEstoque();
            mov.setTipoMovimentacao("SAIDA");
            model.addAttribute("movimentacao", mov);
        }

        model.addAttribute("clientes", clienteRepository.findByUsuario(usuario));
        model.addAttribute("apiarios", apiarioRepository.findByUsuario(usuario));
        return "cadastro-vendas";
    }

    @PostMapping("/cadastro-vendas")
    public String salvarVenda(@ModelAttribute("movimentacao") MovimentacaoEstoque mov,
                              @AuthenticationPrincipal UserDetails userDetails,
                              RedirectAttributes attributes) {
        try {
            Usuario usuario = buscarUsuarioLogado(userDetails);
            mov.setUsuario(usuario);

            if (mov.getDataSaida() == null) mov.setDataSaida(LocalDate.now());

            movimentacaoEstoqueRepository.save(mov);
            attributes.addFlashAttribute("mensagemSucesso", "Venda registrada!");
        } catch (Exception e) {
            attributes.addFlashAttribute("mensagemErro", "Erro: " + e.getMessage());
        }
        return "redirect:/gerenciar/cadastro-vendas";
    }
}