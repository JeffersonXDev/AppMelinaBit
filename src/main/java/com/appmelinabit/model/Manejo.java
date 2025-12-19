package com.appmelinabit.model;

import jakarta.persistence.*;
import java.time.LocalDate;

// Entidade principal para o registro de Manejo
@Entity
@Table(name = "manejo")
public class Manejo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_manejo")
    private Long idManejo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_apiario", nullable = false)
    private Apiario apiario; // Mapeia o objeto Apiario, o que é crucial para o formulário

    @Column(name = "numero_colmeia")
    private String numeroColmeia;

    @Column(name = "data_inspecao")
    private LocalDate dataInspecao;

    // Detalhes da Colmeia
    private Integer quadros;
    private Integer ovo;
    @Column(name = "crias_operculadas")
    private Integer criasOperculadas;
    private Integer polen;
    private Integer necta;
    private Integer mel;
    @Column(name = "cera_nova")
    private Integer ceraNova;
    
    // Status da Rainha/Postura
    @Column(name = "posturas_boas")
    private Boolean posturasBoas;
    @Column(name = "crias_uniformes")
    private Boolean criasUniformes;

    // Alimentação e Manejos
    private Integer alimentador; // 1 para Sim, 0 para Não
    private Float xarope; // Usando Float para números decimais
    @Column(name = "bife_proteico")
    private Integer bifeProteico;
    @Column(name = "rainha_nova")
    private String rainhaNova; // Identificação ou ano

    // Outras Observações
    private String melgueiras;
    @Column(name = "inimigos_naturais")
    private String inimigosNaturais;
    private String obs;

    // Construtor padrão necessário pelo JPA
    public Manejo() {}

    // Getters e Setters para todos os campos (necessários para o Thymeleaf/Spring Binding)

    public Long getIdManejo() { return idManejo; }
    public void setIdManejo(Long idManejo) { this.idManejo = idManejo; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Apiario getApiario() { return apiario; }
    public void setApiario(Apiario apiario) { this.apiario = apiario; }

    public String getNumeroColmeia() { return numeroColmeia; }
    public void setNumeroColmeia(String numeroColmeia) { this.numeroColmeia = numeroColmeia; }

    public LocalDate getDataInspecao() { return dataInspecao; }
    public void setDataInspecao(LocalDate dataInspecao) { this.dataInspecao = dataInspecao; }

    public Integer getQuadros() { return quadros; }
    public void setQuadros(Integer quadros) { this.quadros = quadros; }

    public Integer getOvo() { return ovo; }
    public void setOvo(Integer ovo) { this.ovo = ovo; }

    public Integer getCriasOperculadas() { return criasOperculadas; }
    public void setCriasOperculadas(Integer criasOperculadas) { this.criasOperculadas = criasOperculadas; }

    public Integer getPolen() { return polen; }
    public void setPolen(Integer polen) { this.polen = polen; }

    public Integer getNecta() { return necta; }
    public void setNecta(Integer necta) { this.necta = necta; }

    public Integer getMel() { return mel; }
    public void setMel(Integer mel) { this.mel = mel; }

    public Integer getCeraNova() { return ceraNova; }
    public void setCeraNova(Integer ceraNova) { this.ceraNova = ceraNova; }

    public Boolean getPosturasBoas() { return posturasBoas; }
    public void setPosturasBoas(Boolean posturasBoas) { this.posturasBoas = posturasBoas; }

    public Boolean getCriasUniformes() { return criasUniformes; }
    public void setCriasUniformes(Boolean criasUniformes) { this.criasUniformes = criasUniformes; }

    public Integer getAlimentador() { return alimentador; }
    public void setAlimentador(Integer alimentador) { this.alimentador = alimentador; }

    public Float getXarope() { return xarope; }
    public void setXarope(Float xarope) { this.xarope = xarope; }

    public Integer getBifeProteico() { return bifeProteico; }
    public void setBifeProteico(Integer bifeProteico) { this.bifeProteico = bifeProteico; }

    public String getRainhaNova() { return rainhaNova; }
    public void setRainhaNova(String rainhaNova) { this.rainhaNova = rainhaNova; }

    public String getMelgueiras() { return melgueiras; }
    public void setMelgueiras(String melgueiras) { this.melgueiras = melgueiras; }

    public String getInimigosNaturais() { return inimigosNaturais; }
    public void setInimigosNaturais(String inimigosNaturais) { this.inimigosNaturais = inimigosNaturais; }

    public String getObs() { return obs; }
    public void setObs(String obs) { this.obs = obs; }
}