package com.appmelinabit.repository;

import com.appmelinabit.model.MovimentacaoEstoque;
import com.appmelinabit.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDate;

@Repository
public interface MovimentacaoEstoqueRepository extends JpaRepository<MovimentacaoEstoque, Integer> {

    @Query("SELECT COALESCE(SUM(m.valorVenda), 0) FROM MovimentacaoEstoque m " +
            "WHERE m.usuario = :usuario " +
            "AND m.tipoMovimentacao = 'SAIDA' " +
            "AND m.dataSaida >= :data " +
            "AND (LOWER(m.nome) LIKE LOWER(CONCAT('%', :produto, '%')) OR LOWER(m.nome) LIKE LOWER(CONCAT('%', :produto, 'en%')))")
    BigDecimal sumVendasSemana(@Param("produto") String produto, @Param("usuario") Usuario usuario, @Param("data") LocalDate data);

    @Query("SELECT ABS(COALESCE(SUM(CASE WHEN m.tipoMovimentacao = 'ENTRADA' THEN m.quantidade ELSE -m.quantidade END), 0)) " +
            "FROM MovimentacaoEstoque m " +
            "WHERE m.usuario = :usuario " +
            "AND LOWER(m.nome) LIKE LOWER(CONCAT('%', :produto, '%'))")
    Number calcularEstoquePorProduto(@Param("produto") String produto, @Param("usuario") Usuario usuario);

    @Query("SELECT COALESCE(SUM(m.quantidade), 0) FROM MovimentacaoEstoque m " +
            "WHERE m.usuario = :usuario " +
            "AND m.tipoMovimentacao = 'SAIDA' " +
            "AND (LOWER(m.nome) LIKE LOWER(CONCAT('%', :produto, '%')) OR LOWER(m.nome) LIKE LOWER(CONCAT('%', :produto, 'en%')))")
    BigDecimal sumTotalVendasProduto(@Param("produto") String produto, @Param("usuario") Usuario usuario);
}