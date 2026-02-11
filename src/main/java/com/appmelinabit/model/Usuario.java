package com.appmelinabit.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import java.math.BigDecimal; 
import java.time.LocalDateTime; 

@Entity 
@Table(name = "usuarios")
public class Usuario {

    // CHAVE PRIMÁRIA
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "id_usuario") 
    private Integer idUsuario;
    
    @Column(name = "status")
    private String status = "INATIVO"; // Define o padrão aqui

    @Column(name = "nome")
    private String nome;

    @Column(name = "email")
    private String email;

    @Column(name = "senha")
    private String senha;

    @Column(name = "telefone")
    private String telefone;
    
    @Column(name = "cpf") 
    private String cpf; 

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

    @Column(name = "id_apiario")
    private Integer idApiario;

    @Column(name = "lgpd_aceito")
    private Boolean lgpdAceito = false;

    @Column(name = "valor_recebido")
    private BigDecimal valorRecebido; 

    @Column(name = "nivel")
    private String nivel;

    @Column(name = "status_conta")
    private String statusConta; 

    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @Column(name = "ultimo_login")
    private LocalDateTime ultimoLogin;
    
    public Usuario() { }

    public Integer getIdUsuario() { // CORRIGIDO: Retorna Integer
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) { // CORRIGIDO: Recebe Integer
        this.idUsuario = idUsuario;
    }
    
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    // GETTERS e SETTERS do ENDEREÇO
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

    public String getUf() {return uf;}

    public void setUf(String uf) {this.uf = uf;}
    
    // Outros Getters e Setters
    public Integer getIdApiario() { // CORRIGIDO: Retorna Integer
        return idApiario;
    }

    public void setIdApiario(Integer idApiario) { // CORRIGIDO: Recebe Integer
        this.idApiario = idApiario;
    }

    public Boolean getLgpdAceito() {
        return lgpdAceito;
    }
    public void setLgpdAceito(Boolean lgpdAceito) {
        this.lgpdAceito = lgpdAceito;
    }

    public BigDecimal getValorRecebido() {
        return valorRecebido;
    }

    public void setValorRecebido(BigDecimal valorRecebido) {
        this.valorRecebido = valorRecebido;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public LocalDateTime getUltimoLogin() {
        return ultimoLogin;
    }

    public void setUltimoLogin(LocalDateTime ultimoLogin) {
        this.ultimoLogin = ultimoLogin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getStatusConta() {
        return statusConta;
    }

    public void setStatusConta(String statusConta) {
        this.statusConta = statusConta;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}