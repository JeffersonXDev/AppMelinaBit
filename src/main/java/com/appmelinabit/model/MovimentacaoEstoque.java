package com.appmelinabit.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.math.BigDecimal; 
import jakarta.validation.constraints.DecimalMin;

@Entity
@Table(name = "movimentacao_estoque")
public class MovimentacaoEstoque {

    // 1. CHAVE PRIMÁRIA
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_movimentacao") 
    private Long id; 

    // 2. CHAVES ESTRANGEIRAS (Many-to-One)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
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
    
    // 3. DADOS DO PRODUTO/MOVIMENTAÇÃO
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;
    
    @Column(name = "unidade_medida")
    private String unidadeMedida;
    
    // 4. VALORES E CUSTOS
    @Column(name = "valor_custo")
    private BigDecimal valorCusto; 
    
    @Column(name = "valor_venda")
    private BigDecimal valorVenda; 
    
    @Column(name = "preco_base")
    private BigDecimal precoBase; 
    
    @Column(name = "valor_compra")
    private BigDecimal valorCompra; 
    
    @Column(name = "valor_frete", precision = 10, scale = 2)
    @DecimalMin(value = "0.00", message = "O frete deve ser R$ 0,00 ou maior.")
    private BigDecimal valorFrete;
    
    // 5. DATAS
    @Column(name = "data_compra")
    private LocalDate dataCompra;

    @Column(name = "data_saida")
    private LocalDate dataSaida;

    // --- CONSTRUTOR PADRÃO ---
    public MovimentacaoEstoque() {
    }

    // --- GETTERS E SETTERS COMPLETOS ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Apiario getApiario() {
        return apiario;
    }

    public void setApiario(Apiario apiario) {
        this.apiario = apiario;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public BigDecimal getValorCusto() {
        return valorCusto;
    }

    public void setValorCusto(BigDecimal valorCusto) {
        this.valorCusto = valorCusto;
    }

    public BigDecimal getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(BigDecimal valorVenda) {
        this.valorVenda = valorVenda;
    }

    public BigDecimal getPrecoBase() {
        return precoBase;
    }

    public void setPrecoBase(BigDecimal precoBase) {
        this.precoBase = precoBase;
    }

    public BigDecimal getValorCompra() {
        return valorCompra;
    }

    public void setValorCompra(BigDecimal valorCompra) {
        this.valorCompra = valorCompra;
    }

    public LocalDate getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(LocalDate dataCompra) {
        this.dataCompra = dataCompra;
    }

    public LocalDate getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(LocalDate dataSaida) {
        this.dataSaida = dataSaida;
    }
    public BigDecimal getValorFrete() {
        return valorFrete;
    }

    public void setValorFrete(BigDecimal valorFrete) {
        this.valorFrete = valorFrete;
    }
}