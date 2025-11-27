package com.appmelinabit.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "usuarios")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario") 
	private Long id;

	@Column(name = "id_apiario")
	private Integer idApiario;
	
	@Column(name = "nome", nullable = false)
	private String nome;
	
	@Column(name = "telefone")
	private String telefone;
	
	@Column(name = "email", nullable = false)
	private String email;
	
	@Column(name = "nivel")
	private String nivel;
	
	@Column(name = "senha", nullable = false)
	private String senha;
	
	@Column(name = "data_cadastro")
	private OffsetDateTime dataCadastro;
	
	@Column(name = "status_conta")
	private String statusConta;
	
	@Column(name = "valor_recebido")
	private BigDecimal valorRecebido;
	
	@Column(name = "ultimo_login")
	private OffsetDateTime ultimoLogin;
	
	// Campo LGPD com restrição de nulidade (correto para novos registros)
    @Column(name = "lgpd_aceito", nullable = false)
    private boolean lgpdAceito;

	// Construtores
	public Usuario() {
	}

	// CONSTRUTOR ATUALIZADO
	public Usuario(String nome, String email, String senha, String nivel, boolean lgpdAceito) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.nivel = nivel;
		this.lgpdAceito = lgpdAceito;
	}

	// Getters e Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public OffsetDateTime getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(OffsetDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getStatusConta() {
		return statusConta;
	}

	public void setStatusConta(String statusConta) {
		this.statusConta = statusConta;
	}

	public BigDecimal getValorRecebido() {
		return valorRecebido;
	}

	public void setValorRecebido(BigDecimal valorRecebido) {
		this.valorRecebido = valorRecebido;
	}

	public OffsetDateTime getUltimoLogin() {
		return ultimoLogin;
	}

	public void setUltimoLogin(OffsetDateTime ultimoLogin) {
		this.ultimoLogin = ultimoLogin;
	}

	// Getters e Setters do campo LGPD
    public boolean isLgpdAceito() {
        return lgpdAceito;
    }

    public void setLgpdAceito(boolean lgpdAceito) {
        this.lgpdAceito = lgpdAceito;
    }
}