package com.appmelinabit.repository;

import com.appmelinabit.model.MaterialApicola;
import com.appmelinabit.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;

@Repository
public interface MaterialApicolaRepository extends JpaRepository<MaterialApicola, Integer> {

    // 1. Soma quanto foi gasto em compras de materiais
    @Query("SELECT COALESCE(SUM(m.valorPago), 0) FROM MaterialApicola m WHERE m.usuario = :usuario AND m.tipoMovimentacao = 'ENTRADA'")
    BigDecimal sumValorGasto(@Param("usuario") Usuario usuario);

    // 2. Calcula o saldo total do almoxarifado (Geral)
    @Query("SELECT COALESCE(SUM(m.quantidadeComprada - m.quantidadeEmUso), 0.0) FROM MaterialApicola m WHERE m.usuario = :usuario")
    Double calcularSaldoGeralMateriais(@Param("usuario") Usuario usuario);

    // 3. AJUSTADO: Nome do método alterado para bater com o Controller
    @Query("SELECT COALESCE(SUM(m.quantidadeComprada - m.quantidadeEmUso), 0.0) " +
            "FROM MaterialApicola m WHERE m.usuario = :usuario AND LOWER(m.nomeMaterial) LIKE LOWER(CONCAT('%', :nome, '%'))")
    Double calcularSaldoMaterial(@Param("nome") String nome, @Param("usuario") Usuario usuario);
}