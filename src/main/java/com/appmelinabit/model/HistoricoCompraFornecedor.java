package com.appmelinabit.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.math.BigDecimal;

@Entity
@Table(name = "historico_compras_fornecedor") // Tabela de histórico de transações
public class HistoricoCompraFornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Chave primária da transação

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_fornecedor", nullable = false)
    private Fornecedor fornecedor;

    @Column(name = "material_comprado")
    private String materialComprado;

    @Column(name = "quantidade_comprado")
    private Integer quantidadeComprado;
    
    @Column(name = "preco_pago")
    private BigDecimal precoPago;

    @Column(name = "valor_frete")
    private BigDecimal valorFrete;

    @Column(name = "ultima_compra")
    private LocalDate ultimaCompra; 

}