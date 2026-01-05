package com.appmelinabit.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity 
@Table(name = "apiarios")
public class Apiario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_apiario")
    private Integer idApiario;
    
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "id_usuario", nullable = false) 
    private Usuario usuario; 
    
    @Column(name = "nome")
    private String nome; 

    @Column(name = "qntd_colmeias")
    private Integer qntdColmeias;

    @Column(name = "data_instalacao")
    private LocalDate dataInstalacao; 

    @Column(name = "endereco")
    private String endereco;
    
    @Column(name = "numero")
    private String numero;
    
    @Column(name = "em_terra_terceiros")
    private Boolean emTerraTerceiros; 

    @Column(name = "obs")
    private String obs;

    @Column(name = "data_visita")
    private LocalDate dataVisita; 
    
    @Column(name = "cidade")
    private String cidade;

    @Column(name = "estado") 
    private String estado;
    

    public Apiario() {}

    public Integer getIdApiario() {
        return idApiario;
    }
    public void setIdApiario(Integer idApiario) {
        this.idApiario = idApiario;
    }

    public Integer getId() { 
        return idApiario;
    }
    public void setId(Integer idApiario) { 
        this.idApiario = idApiario;
    }

    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuarioLogado) {
        this.usuario = usuarioLogado;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getQntdColmeias() {
        return qntdColmeias;
    }
    public void setQntdColmeias(Integer qntdColmeias) {
        this.qntdColmeias = qntdColmeias;
    }

    public LocalDate getDataInstalacao() {
        return dataInstalacao;
    }
    public void setDataInstalacao(LocalDate dataInstalacao) {
        this.dataInstalacao = dataInstalacao;
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

    public Boolean getEmTerraTerceiros() {
        return emTerraTerceiros;
    }
    public void setEmTerraTerceiros(Boolean emTerraTerceiros) {
        this.emTerraTerceiros = emTerraTerceiros;
    }

    public String getObs() {
        return obs;
    }
    public void setObs(String obs) {
        this.obs = obs;
    }

    public LocalDate getDataVisita() {
        return dataVisita;
    }
    public void setDataVisita(LocalDate dataVisita) {
        this.dataVisita = dataVisita;
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
}