package com.appmelinabit.model;

import jakarta.persistence.*;
import java.time.LocalDate;

// REMOVIDO: import lombok.Getter;
// REMOVIDO: import lombok.Setter;
// REMOVIDO: import lombok.NoArgsConstructor;
// REMOVIDO: import lombok.AllArgsConstructor;

@Entity
@Table(name = "clientes")
public class Cliente {

    // Chave Primária
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long id;

    // Chave Estrangeira: Referência ao Usuário que cadastrou
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario; 
    
    // Dados do Cliente
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

    // ===================================================================
    // CONSTRUTORES (Substituindo @NoArgsConstructor e @AllArgsConstructor)
    // ===================================================================
    
    // Construtor sem argumentos (NoArgsConstructor)
    public Cliente() {
    }

    // Construtor com todos os argumentos (AllArgsConstructor - Ajuste se necessário, mas geralmente não é usado com ID auto-gerado)
    // Você pode omitir este construtor se não precisar dele explicitamente.
    public Cliente(Long id, Usuario usuario, String nomeCliente, String endereco, String numero, String cidade, String estado, String uf, LocalDate dataCompra, String telefone, String email) {
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
    
    // ===================================================================
    // GETTERS & SETTERS (Substituindo @Getter e @Setter)
    // ===================================================================
    
    // ID
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    // Usuario (Você já tinha o setter)
    public Usuario getUsuario() { return usuario; }
	public void setUsuario(Usuario usuarioLogado) { this.usuario = usuarioLogado; }
    
    // Nome Cliente
    public String getNomeCliente() { return nomeCliente; }
    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }

    // Endereco (O CAMPO QUE ESTAVA CAUSANDO O ERRO AGORA)
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    // Numero
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    // Cidade
    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    // Estado
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    // UF
    public String getUf() { return uf; }
    public void setUf(String uf) { this.uf = uf; }

    // Data Compra
    public LocalDate getDataCompra() { return dataCompra; }
    public void setDataCompra(LocalDate dataCompra) { this.dataCompra = dataCompra; }

    // Telefone
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    // Email
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}