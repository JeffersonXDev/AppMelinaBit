package com.appmelinabit.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "manejos")
public class Manejo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_manejo")
    private Integer idManejo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_apiario", nullable = false)
    private Apiario apiario;

    @Column(name = "numero_colmeia")
    private String numeroColmeia;

    @Column(name = "data_inspecao")
    private LocalDate dataInspecao;

    // ==========================================
    // BLOCO 1: ESTADO DA COLMEIA (NÚMEROS)
    // ==========================================
    
    @Column(name = "quadros_mel")
    private String quadrosMel;
    
    @Column(name = "quadros_ovo")
    private String quadrosOvo;
    
    @Column(name = "crias_operculadas")
    private String criasOperculadas;
   

    // ==========================================
    // BLOCO 2: AVALIAÇÃO RÁPIDA (TEXTO SIM/NÃO)
    // Mudei de Integer para String para aceitar seu HTML
    // ==========================================
    @Column(name = "polen")
    private String polen;
    
    @Column(name = "nectar")
    private String nectar;
    
    @Column(name = "propolis")
    private String propolis;
    
    @Column(name = "cera_nova")
    private String ceraNova;
    
    @Column(name = "posturasboas")
    private String posturasBoas; 
    
    @Column(name = "criasabertas")
    private String criasAbertas;   
    
    @Column(name = "rainha_nova")
    private String rainhaNova; 
        
    @Column(name = "crias_uniformes")
    private String criasUniformes;
    

    // ==========================================
    // BLOCO 3: TRATOS E MANUTENÇÃO
    // ==========================================
    private String alimentador; // MUDADO PARA STRING (Aceita "Sim"/"Não")
    
    private String xarope; 
    
    @Column(name = "bife_proteico")
    private String bifeProteico; // Mantive Integer pois parece ser quantidade

    // ==========================================
    // BLOCO 4: OBSERVAÇÕES E INIMIGOS
    // ==========================================
    private String melgueiras;
    
    @Column(name = "inimigos_naturais")
    private String inimigosNaturais;
    
    @Column(name = "obs")
    private String obs;

    public Manejo() {}

    // Getters e Setters atualizados para String onde necessário
    public Integer getIdManejo() { return idManejo; }
    public void setIdManejo(Integer idManejo) { this.idManejo = idManejo; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public Apiario getApiario() { return apiario; }
    public void setApiario(Apiario apiario) { this.apiario = apiario; }
    public String getNumeroColmeia() { return numeroColmeia; }
    public void setNumeroColmeia(String numeroColmeia) { this.numeroColmeia = numeroColmeia; }
    public LocalDate getDataInspecao() { return dataInspecao; }
    public void setDataInspecao(LocalDate dataInspecao) { this.dataInspecao = dataInspecao; }
    public String getQuadrosOvo() { return quadrosOvo; }
    public void setQuadrosOvo(String quadrosOvo) { this.quadrosOvo = quadrosOvo; }    
    public String getQuadrosMel() { return quadrosMel; }
    public void setQuadrosMel(String quadrosMel) { this.quadrosMel = quadrosMel; }
    public String getCriasOperculadas() { return criasOperculadas; }
    public void setCriasOperculadas(String criasOperculadas) { this.criasOperculadas = criasOperculadas; }
    public String getPolen() { return polen; }
    public void setPolen(String polen) { this.polen = polen; }
    public String getNectar() { return nectar; }
    public void setNectar(String nectar) { this.nectar = nectar; }
    public String getPropolis() { return propolis; }
    public void setPropolis(String propolis) { this.propolis = propolis; }
    public String getCeraNova() { return ceraNova; }
    public void setCeraNova(String ceraNova) { this.ceraNova = ceraNova; }
    public String getPosturasBoas() { return posturasBoas; }
    public void setPosturasBoas(String posturasBoas) { this.posturasBoas = posturasBoas; }
    public String getCriasUniformes() { return criasUniformes; }
    public void setCriasUniformes(String criasUniformes) { this.criasUniformes = criasUniformes; }
    public String getCriasAbertas() { return criasAbertas; }
    public void setCriasAbertas(String criasAbertas) { this.criasAbertas = criasAbertas; } 
    public String getAlimentador() { return alimentador; }
    public void setAlimentador(String alimentador) { this.alimentador = alimentador; }
    public String getXarope() { return xarope; }
    public void setXarope(String xarope) { this.xarope = xarope; }
    public String getBifeProteico() { return bifeProteico; }
    public void setBifeProteico(String bifeProteico) { this.bifeProteico = bifeProteico; }
    public String getRainhaNova() { return rainhaNova; }
    public void setRainhaNova(String rainhaNova) { this.rainhaNova = rainhaNova; }
    public String getMelgueiras() { return melgueiras; }
    public void setMelgueiras(String melgueiras) { this.melgueiras = melgueiras; }
    public String getInimigosNaturais() { return inimigosNaturais; }
    public void setInimigosNaturais(String inimigosNaturais) { this.inimigosNaturais = inimigosNaturais; }
    public String getObs() { return obs; }
    public void setObs(String obs) { this.obs = obs; }
}