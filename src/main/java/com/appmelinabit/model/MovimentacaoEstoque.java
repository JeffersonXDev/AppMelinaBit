package com.appmelinabit.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

// Garante que o Java encontre as outras classes
import com.appmelinabit.model.Cliente;
import com.appmelinabit.model.Fornecedor;
import com.appmelinabit.model.Usuario;
import com.appmelinabit.model.Apiario;

@Entity
@Table(name = "movimentacao_estoque")
public class MovimentacaoEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimentacao")
    private Integer idMovimentacao;

    // Relacionamentos com objetos (Essenciais para o Service/Controller)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_fornecedor")
    private Fornecedor fornecedor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_apiario")
    private Apiario apiario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @Column(name = "tipo_movimentacao")
    private String tipoMovimentacao;

    private String nome;
    private String descricao;
    private Double quantidade;

    @Column(name = "unidade_medida")
    private String unidadeMedida;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "data_entrada")
    private LocalDate dataEntrada;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "data_saida")
    private LocalDate dataSaida;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "data_compra")
    private LocalDate dataCompra;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;

    @Column(name = "valor_compra")
    private Double valorCompra;

    @Column(name = "valor_pago")
    private Double valorPago;

    @Column(name = "valor_unitario")
    private Double valorUnitario;

    @Column(name = "valor_frete")
    private Double valorFrete;

    @Column(name = "valor_custo")
    private Double valorCusto;

    @Column(name = "preco_base")
    private Double precoBase;

    @Column(name = "valor_venda")
    private Double valorVenda;

    @Column(columnDefinition = "TEXT")
    private String obs;

    // --- GETTERS E SETTERS DOS CAMPOS ---

    public Integer getIdMovimentacao() { return idMovimentacao; }
    public void setIdMovimentacao(Integer idMovimentacao) { this.idMovimentacao = idMovimentacao; }

    public Fornecedor getFornecedor() { return fornecedor; }
    public void setFornecedor(Fornecedor fornecedor) { this.fornecedor = fornecedor; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Apiario getApiario() { return apiario; }
    public void setApiario(Apiario apiario) { this.apiario = apiario; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public String getTipoMovimentacao() { return tipoMovimentacao; }
    public void setTipoMovimentacao(String tipoMovimentacao) { this.tipoMovimentacao = tipoMovimentacao; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Double getQuantidade() { return quantidade; }
    public void setQuantidade(Double quantidade) { this.quantidade = quantidade; }

    public String getUnidadeMedida() { return unidadeMedida; }
    public void setUnidadeMedida(String unidadeMedida) { this.unidadeMedida = unidadeMedida; }

    public LocalDate getDataEntrada() { return dataEntrada; }
    public void setDataEntrada(LocalDate dataEntrada) { this.dataEntrada = dataEntrada; }

    public LocalDate getDataSaida() { return dataSaida; }
    public void setDataSaida(LocalDate dataSaida) { this.dataSaida = dataSaida; }

    public LocalDate getDataCompra() { return dataCompra; }
    public void setDataCompra(LocalDate dataCompra) { this.dataCompra = dataCompra; }

    public LocalDate getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(LocalDate dataCadastro) { this.dataCadastro = dataCadastro; }

    public Double getValorCompra() { return valorCompra; }
    public void setValorCompra(Double valorCompra) { this.valorCompra = valorCompra; }

    public Double getValorPago() { return valorPago; }
    public void setValorPago(Double valorPago) { this.valorPago = valorPago; }

    public Double getValorUnitario() { return valorUnitario; }
    public void setValorUnitario(Double valorUnitario) { this.valorUnitario = valorUnitario; }

    public Double getValorFrete() { return valorFrete; }
    public void setValorFrete(Double valorFrete) { this.valorFrete = valorFrete; }

    public Double getValorCusto() { return valorCusto; }
    public void setValorCusto(Double valorCusto) { this.valorCusto = valorCusto; }

    public Double getPrecoBase() { return precoBase; }
    public void setPrecoBase(Double precoBase) { this.precoBase = precoBase; }

    public Double getValorVenda() { return valorVenda; }
    public void setValorVenda(Double valorVenda) { this.valorVenda = valorVenda; }

    public String getObs() { return obs; }
    public void setObs(String obs) { this.obs = obs; }

    // --- MÉTODOS DE COMPATIBILIDADE (O QUE O SERVICE E CONTROLLER CHAMAM) ---

    public Integer getIdUsuario() {
        return (this.usuario != null) ? this.usuario.getIdUsuario() : null;
    }

    public void setIdUsuario(Integer idUsuario) {
        if (idUsuario != null) {
            if (this.usuario == null) this.usuario = new Usuario();
            this.usuario.setIdUsuario(idUsuario);
        }
    }

    public void setIdFornecedor(Integer idFornecedor) {
        if (idFornecedor != null) {
            if (this.fornecedor == null) this.fornecedor = new Fornecedor();
            // Na sua classe Fornecedor o campo é 'id', então o setter é 'setId'
            this.fornecedor.setId(idFornecedor);
        }
    }

    public void setIdCliente(Integer idCliente) {
        if (idCliente != null) {
            if (this.cliente == null) this.cliente = new Cliente();
            // Na sua classe Cliente o campo é 'id', então o setter é 'setId'
            this.cliente.setId(idCliente);
        }
    }

    public void setIdApiario(Integer idApiario) {
        if (idApiario != null) {
            if (this.apiario == null) this.apiario = new Apiario();
            // Verifique se na classe Apiario o setter é setIdApiario ou setId
            this.apiario.setIdApiario(idApiario);
        }
    }
}