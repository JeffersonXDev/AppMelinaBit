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

    // =========================================================================
    // BLOCO 1: LISTAGEM SIMPLES (Usado nas tabelas de histórico)
    // =========================================================================
    List<MovimentacaoEstoque> findByUsuarioOrderByDataEntradaDesc(Usuario usuario);

    // =========================================================================
    // BLOCO 2: BUSCA GLOBAL (Alimenta o campo 'keyword' de todas as páginas)
    // =========================================================================
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

    // =========================================================================
    // BLOCO 3: ESTOQUE E INSUMOS (Alimenta Widgets de kg e saldo de materiais)
    // =========================================================================
    @Query("SELECT COALESCE(SUM(CASE " +
            "WHEN UPPER(m.tipoMovimentacao) = 'SAIDA' THEN -m.quantidade " +
            "ELSE m.quantidade END), 0.0) " +
            "FROM MovimentacaoEstoque m " +
            "WHERE m.usuario = :usuario " +
            "AND (LOWER(m.nome) = LOWER(:produto) OR m.nome LIKE %:produto%)")
    BigDecimal calcularEstoquePorProduto(@Param("produto") String produto, @Param("usuario") Usuario usuario);

    // Mantido para compatibilidade se algum HTML chamar especificamente este nome
    default Double calcularSaldoInsumo(String nomeInsumo, Usuario usuario) {
        return calcularEstoquePorProduto(nomeInsumo, usuario).doubleValue();
    }

    // =========================================================================
    // BLOCO 4: VENDAS 7 DIAS (Alimenta Widgets de R$ do Dashboard)
    // =========================================================================
    @Query("SELECT COALESCE(SUM(m.valorVenda), 0) FROM MovimentacaoEstoque m " +
            "WHERE m.usuario = :usuario " +
            "AND UPPER(m.tipoMovimentacao) = 'SAIDA' " + 
            "AND m.dataCadastro >= :data " + 
            "AND LOWER(m.nome) = LOWER(:produto)")
    BigDecimal sumVendasSemana(@Param("produto") String produto,
                               @Param("usuario") Usuario usuario,
                               @Param("data") LocalDate data);
}