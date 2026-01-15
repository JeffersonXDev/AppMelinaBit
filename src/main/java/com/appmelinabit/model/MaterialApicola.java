package com.appmelinabit.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "materiais_apicolas")
public class MaterialApicola {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(name = "nome_material")
    private String nomeMaterial;

    @Column(name = "quantidade_comprada")
    private Double quantidadeComprada;

    @Column(name = "quantidade_em_uso")
    private Double quantidadeEmUso = 0.0; // Valor inicial padrão

    @Column(name = "data_compra")
    private LocalDate dataCompra;

    @Column(name = "valor_pago")
    private BigDecimal valorPago;

    @Column(name = "tipo_movimentacao") // Campo útil para filtros, caso exista no banco
    private String tipoMovimentacao;

    @Column(name = "obs")
    private String obs;

    public MaterialApicola() {}

    // --- GETTERS E SETTERS ---

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public String getNomeMaterial() { return nomeMaterial; }
    public void setNomeMaterial(String nomeMaterial) { this.nomeMaterial = nomeMaterial; }

    public Double getQuantidadeComprada() { return quantidadeComprada; }
    public void setQuantidadeComprada(Double quantidadeComprada) { this.quantidadeComprada = quantidadeComprada; }

    public Double getQuantidadeEmUso() { return quantidadeEmUso; }
    public void setQuantidadeEmUso(Double quantidadeEmUso) { this.quantidadeEmUso = quantidadeEmUso; }

    public LocalDate getDataCompra() { return dataCompra; }
    public void setDataCompra(LocalDate dataCompra) { this.dataCompra = dataCompra; }

    public BigDecimal getValorPago() { return valorPago; }
    public void setValorPago(BigDecimal valorPago) { this.valorPago = valorPago; }

    public String getTipoMovimentacao() { return tipoMovimentacao; }
    public void setTipoMovimentacao(String tipoMovimentacao) { this.tipoMovimentacao = tipoMovimentacao; }

    public String getObs() {
        return obs;
    }
    public void setObs(String obs) {
        this.obs = obs;
    }
}