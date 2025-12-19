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

    // 1. Relacionamento com o Fornecedor
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_fornecedor", nullable = false)
    private Fornecedor fornecedor;
     
    // 3. Dados da Transação
    @Column(name = "material_comprado")
    private String materialComprado; // Nome textual (Se não usar a Entidade Produto)

    @Column(name = "quantidade_comprado")
    private Integer quantidadeComprado;
    
    @Column(name = "preco_pago")
    private BigDecimal precoPago; // O preço total ou unitário pago (Use BigDecimal)

    @Column(name = "valor_frete")
    private BigDecimal valorFrete;

    @Column(name = "ultima_compra") // Mantendo o nome da coluna do seu DB
    private LocalDate ultimaCompra; 

    // --- Construtores, Getters e Setters aqui ---
}