package com.appmelinabit.repository;

import com.appmelinabit.model.Manejo;
import com.appmelinabit.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ManejoRepository extends JpaRepository<Manejo, Integer> {

    // 1. Busca básica por objeto Usuario
    List<Manejo> findByUsuario(Usuario usuario);

    // 2. RESOLUÇÃO DO ERRO: Busca pelo ID do usuário (navegando na entidade)
    // O Spring entende que deve buscar idUsuario dentro da entidade Usuario vinculada ao Manejo
    List<Manejo> findByUsuario_IdUsuario(Integer idUsuario);

    // 3. Busca ordenada para o Gerenciamento
    List<Manejo> findByUsuarioOrderByDataInspecaoDesc(Usuario usuario);

    // 4. Busca customizada com Keyword
    @Query("SELECT m FROM Manejo m WHERE m.usuario = :usuario AND " +
            "(LOWER(m.numeroColmeia) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(m.apiario.nome) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Manejo> buscarManejos(@Param("keyword") String keyword, @Param("usuario") Usuario usuario);
}