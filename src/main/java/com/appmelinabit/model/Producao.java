package com.appmelinabit.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.math.BigDecimal;

@Entity
@Table(name = "producoes") // Tabela nova, separada de movimentacao_estoque
public class Producao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_apiario")
    private Apiario apiario;

    // Aqui usamos o nome do produto (Mel, Cera, etc.)
    // Pode ser uma String agora para simplificar ou o Model Produto
    private String tipoProduto;

    private BigDecimal quantidade;

    private LocalDate dataColheita;

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTipoProduto() { return tipoProduto; }
    public void setTipoProduto(String tipoProduto) { this.tipoProduto = tipoProduto; }

    public BigDecimal getQuantidade() { return quantidade; }
    public void setQuantidade(BigDecimal quantidade) { this.quantidade = quantidade; }

    public LocalDate getDataColheita() { return dataColheita; }
    public void setDataColheita(LocalDate dataColheita) { this.dataColheita = dataColheita; }

    public Apiario getApiario() { return apiario; }
    public void setApiario(Apiario apiario) { this.apiario = apiario; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}