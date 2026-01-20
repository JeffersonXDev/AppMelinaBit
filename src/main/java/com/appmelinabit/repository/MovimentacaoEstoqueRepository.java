package com.appmelinabit.repository;

import com.appmelinabit.model.MovimentacaoEstoque;
import com.appmelinabit.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovimentacaoEstoqueRepository extends JpaRepository<MovimentacaoEstoque, Integer> {

    List<MovimentacaoEstoque> findByUsuarioOrderByDataEntradaDesc(Usuario usuario);

    /**
     * Motor de busca unificado.
     * Importante: Usamos o objeto de leitura (ex: f.nomeFornecedor) para a busca.
     */
    @Query("SELECT m FROM MovimentacaoEstoque m " +
            "LEFT JOIN m.apiario a " +
            "LEFT JOIN m.fornecedor f " +
            "LEFT JOIN m.cliente c " +
            "WHERE m.usuario = :usuario AND (" +
            "LOWER(m.nome) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(m.tipoMovimentacao) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(m.obs) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(a.nome) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(f.nomeFornecedor) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.nomeCliente) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
            ") ORDER BY m.dataEntrada DESC, m.idMovimentacao DESC")
    List<MovimentacaoEstoque> buscarTudo(@Param("keyword") String keyword, @Param("usuario") Usuario usuario);

    /**
     * Cálculo de saldo corrigido.
     * Alterado de Integer para Double para suportar frações (ex: 1.5kg de cera).
     */
    @Query("SELECT COALESCE(SUM(CASE " +
            "WHEN m.tipoMovimentacao = 'ENTRADA' THEN m.quantidade " +
            "WHEN m.tipoMovimentacao = 'SAIDA' THEN -m.quantidade " +
            "ELSE 0 END), 0.0) " +
            "FROM MovimentacaoEstoque m " +
            "WHERE m.usuario = :usuario " +
            "AND LOWER(m.nome) = LOWER(:nomeInsumo)")
    Double calcularSaldoInsumo(@Param("nomeInsumo") String nomeInsumo, @Param("usuario") Usuario usuario);

    /**
     * Soma de vendas por período e produto.
     * Ajustado m.usuario para m.usuario.idUsuario se necessário,
     * mas mantendo a lógica de objeto para JPA.
     */
    @Query("SELECT COALESCE(SUM(m.valorVenda), 0) FROM MovimentacaoEstoque m " +
            "WHERE m.usuario = :usuario " +
            "AND m.tipoMovimentacao = 'SAIDA' " +
            "AND m.dataSaida >= :data " +
            "AND LOWER(m.nome) LIKE LOWER(CONCAT('%', :produto, '%'))")
    BigDecimal sumVendasSemana(@Param("produto") String produto,
                               @Param("usuario") Usuario usuario,
                               @Param("data") LocalDate data);

    /**
     * Cálculo geral de estoque físico.
     * Retorna Double para evitar erros de cast com quantidades decimais.
     */
    @Query("SELECT COALESCE(SUM(CASE " +
            "WHEN m.tipoMovimentacao IN ('ENTRADA', 'COLHEITA', 'COMPRA') THEN m.quantidade " +
            "ELSE -m.quantidade END), 0.0) " +
            "FROM MovimentacaoEstoque m " +
            "WHERE m.usuario = :usuario " +
            "AND LOWER(m.nome) LIKE LOWER(CONCAT('%', :produto, '%'))")
    Double calcularEstoquePorProduto(@Param("produto") String produto,
                                     @Param("usuario") Usuario usuario);
}