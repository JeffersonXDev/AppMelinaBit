package com.appmelinabit.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Integer id; // CORRIGIDO: Agora é Integer

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "nome_cliente", nullable = false)
    private String nomeCliente;

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

    @Column(name = "data_compra")
    private LocalDate dataCompra;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "email")
    private String email;

    public Cliente() {
    }

    // Construtor CORRIGIDO para aceitar Integer
    public Cliente(Integer id, Usuario usuario, String nomeCliente, String endereco, String numero, String cidade, String estado, String uf, LocalDate dataCompra, String telefone, String email) {
        this.id = id;
        this.usuario = usuario;
        this.nomeCliente = nomeCliente;
        this.endereco = endereco;
        this.numero = numero;
        this.cidade = cidade;
        this.estado = estado;
        this.uf = uf;
        this.dataCompra = dataCompra;
        this.telefone = telefone;
        this.email = email;
    }

    // Getters e Setters CORRIGIDOS para Integer
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuarioLogado) { this.usuario = usuarioLogado; }

    public String getNomeCliente() { return nomeCliente; }
    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }

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

    public LocalDate getDataCompra() { return dataCompra; }
    public void setDataCompra(LocalDate dataCompra) { this.dataCompra = dataCompra; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}