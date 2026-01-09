package com.appmelinabit.repository;

import com.appmelinabit.model.Producao;
import com.appmelinabit.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.math.BigDecimal;

@Repository
public interface ProducaoRepository extends JpaRepository<Producao, Integer> {
    // Busca todas as produções de um usuário específico
    List<Producao> findByUsuario(Usuario usuario);

    @Query("SELECT COALESCE(SUM(p.quantidade), 0) FROM Producao p " +
            "WHERE p.usuario = :usuario " +
            "AND LOWER(p.tipoProduto) LIKE LOWER(CONCAT('%', :produto, '%'))")
    BigDecimal somarProducaoPorProduto(@Param("usuario") Usuario usuario, @Param("produto") String produto);
}