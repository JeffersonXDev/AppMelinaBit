package com.appmelinabit.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "fornecedores")
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fornecedor")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "nome_fornecedor", nullable = false)
    private String nomeFornecedor;

    @Column(name = "cnpj", unique = true)
    private String cnpj;

    @Column(name = "endereco")
    private String endereco;

    @Column(name = "numero")
    private String numero;

    @Column(name = "cidade")
    private String cidade;

    @Column(name = "estado")
    private String estado;

    @Column(name = "uf", length = 2)
    private String uf;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "email")
    private String email;

    @Column(name = "vendedor")
    private String vendedor;

    @Column(name = "material_comprado")
    private String materialComprado;

    @Column(name = "quantidade")
    private Integer quantidade;

    @Column(name = "preco_pago")
    private BigDecimal precoPago;

    @Column(name = "valor_frete")
    private BigDecimal valorFrete;

    @Column(name = "obs")
    private String obs;

    @Column(name = "segmento")
    private String segmento;
    // NOVO CAMPO: Timestamp de Cadastro
    @Column(name = "data_cadastro", updatable = false)
    private LocalDateTime dataCadastro;

    // Lógica para preencher a data automaticamente ao salvar
    @PrePersist
    protected void onCreate() {
        this.dataCadastro = LocalDateTime.now();
    }

    public Fornecedor() {
    }

    // --- GETTERS E SETTERS ---

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public String getNomeFornecedor() { return nomeFornecedor; }
    public void setNomeFornecedor(String nomeFornecedor) { this.nomeFornecedor = nomeFornecedor; }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getUf() { return uf; }
    public void setUf(String uf) { this.uf = uf; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getVendedor() { return vendedor; }
    public void setVendedor(String vendedor) { this.vendedor = vendedor; }

    public String getMaterialComprado() { return materialComprado; }
    public void setMaterialComprado(String materialComprado) { this.materialComprado = materialComprado; }

    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }

    public BigDecimal getPrecoPago() { return precoPago; }
    public void setPrecoPago(BigDecimal precoPago) { this.precoPago = precoPago; }

    public BigDecimal getValorFrete() { return valorFrete; }
    public void setValorFrete(BigDecimal valorFrete) { this.valorFrete = valorFrete; }

    public String getObs() { return obs; }
    public void setObs(String obs) { this.obs = obs; }

    public LocalDateTime getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(LocalDateTime dataCadastro) { this.dataCadastro = dataCadastro; }

    public String getSegmento() {return segmento;}

    public void setSegmento(String segmento) {this.segmento = segmento;}
}