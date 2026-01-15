package com.appmelinabit.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.math.BigDecimal;

@Entity
@Table(name = "movimentacao_estoque")
public class MovimentacaoEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimentacao")
    private Integer id; // Padronizado como Integer

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_fornecedor")
    private Fornecedor fornecedor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_apiario")
    private Apiario apiario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @Column(name = "obs")
    private String obs;

    @Column(name = "nome", nullable = false)
    private String nome;

    private String descricao;
    private Integer quantidade;
    private String unidadeMedida;
    private String tipoMovimentacao;

    private BigDecimal valorCusto;
    private BigDecimal valorVenda;
    private BigDecimal valorCompra;

    @Column(precision = 10, scale = 2)
    private BigDecimal valorFrete;

    private LocalDate dataCompra;
    private LocalDate dataSaida;

    public MovimentacaoEstoque() {}

    // --- GETTERS E SETTERS CORRIGIDOS ---

    public Integer getId() { return id; } // Ajustado para Integer
    public void setId(Integer id) { this.id = id; } // Ajustado para Integer

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Fornecedor getFornecedor() { return fornecedor; }
    public void setFornecedor(Fornecedor fornecedor) { this.fornecedor = fornecedor; }

    public Apiario getApiario() { return apiario; }
    public void setApiario(Apiario apiario) { this.apiario = apiario; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }

    public String getUnidadeMedida() { return unidadeMedida; }
    public void setUnidadeMedida(String unidadeMedida) { this.unidadeMedida = unidadeMedida; }

    public String getTipoMovimentacao() { return tipoMovimentacao; }
    public void setTipoMovimentacao(String tipoMovimentacao) { this.tipoMovimentacao = tipoMovimentacao; }

    public BigDecimal getValorCusto() { return valorCusto; }
    public void setValorCusto(BigDecimal valorCusto) { this.valorCusto = valorCusto; }

    public BigDecimal getValorVenda() { return valorVenda; }
    public void setValorVenda(BigDecimal valorVenda) { this.valorVenda = valorVenda; }

    public BigDecimal getValorCompra() { return valorCompra; }
    public void setValorCompra(BigDecimal valorCompra) { this.valorCompra = valorCompra; }

    public BigDecimal getValorFrete() { return valorFrete; }
    public void setValorFrete(BigDecimal valorFrete) { this.valorFrete = valorFrete; }

    public LocalDate getDataCompra() { return dataCompra; }
    public void setDataCompra(LocalDate dataCompra) { this.dataCompra = dataCompra; }

    public LocalDate getDataSaida() { return dataSaida; }
    public void setDataSaida(LocalDate dataSaida) { this.dataSaida = dataSaida; }

    public String getObs() {
        return obs;
    }
    public void setObs(String obs) {
        this.obs = obs;
    }
}