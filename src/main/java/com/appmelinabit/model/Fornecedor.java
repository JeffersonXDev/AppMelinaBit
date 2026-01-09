package com.appmelinabit.model;
import java.math.BigDecimal;
import jakarta.persistence.*;

@Entity
@Table(name = "fornecedores")
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fornecedor")
    private Integer id; // MUDADO de Long para Integer

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario; 

    @Column(name = "nome_fornecedor", nullable = false)
    private String nomeFornecedor;

    @Column(name = "cnpj", unique = true)
    private String cnpj;

    @Column(name = "endereco")
    private String endereco;
    
    // NOVOS CAMPOS DE ENDEREÇO/CONTATO:
    @Column(name = "numero")
    private String numero;
    
    @Column(name = "cidade")
    private String cidade;
    
    @Column(name = "estado")
    private String estado;
    
    @Column(name = "telefone")
    private String telefone;

    @Column(name = "email")
    private String email;
    
    @Column(name = "vendedor")
    private String vendedor; // Nome do contato/vendedor

    @Column(name = "material_comprado")
    private String materialComprado;
    
    @Column(name = "quantidade")
    private Integer quantidade;

    @Column(name = "preco_pago")
    private BigDecimal precoPago; 

    @Column(name = "valor_frete")
    private BigDecimal valorFrete; 

    public Fornecedor() {
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuarioLogado) {
        this.usuario = usuarioLogado;
    }

    public String getNomeFornecedor() {
        return nomeFornecedor;
    }

    public void setNomeFornecedor(String nomeFornecedor) {
        this.nomeFornecedor = nomeFornecedor;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public String getMaterialComprado() {
        return materialComprado;
    }

    public void setMaterialComprado(String materialComprado) {
        this.materialComprado = materialComprado;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    // Setter corrigido para BigDecimal
    public BigDecimal getPrecoPago() {
        return precoPago;
    }

    public void setPrecoPago(BigDecimal precoPago) {
        this.precoPago = precoPago;
    }

    // Setter corrigido para BigDecimal
    public BigDecimal getValorFrete() {
        return valorFrete;
    }

    public void setValorFrete(BigDecimal valorFrete) {
        this.valorFrete = valorFrete;
    }

}