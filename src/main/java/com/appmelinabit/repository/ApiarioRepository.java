package com.appmelinabit.repository;

import com.appmelinabit.model.Apiario;
import com.appmelinabit.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ApiarioRepository extends JpaRepository<Apiario, Integer> {

    // Lista apenas os apiários do usuário logado
    List<Apiario> findByUsuario(Usuario usuario);

    // Conta quantos apiários o usuário tem (Widget Dashboard)
    long countByUsuario(Usuario usuario);

    // Soma a quantidade total de colmeias do usuário (Widget Dashboard)
    @Query("SELECT SUM(a.qntdColmeias) FROM Apiario a WHERE a.usuario = :usuario")
    Integer sumColmeiasByUsuario(@Param("usuario") Usuario usuario);

    // Busca por nome ou cidade (Filtro do CRUD)
    List<Apiario> findByNomeContainingIgnoreCaseOrCidadeContainingIgnoreCase(String nome, String cidade);

    // VERSÃO MELHORADA: Busca por nome/cidade filtrando apenas os do usuário logado
    @Query("SELECT a FROM Apiario a WHERE a.usuario = :usuario AND " +
            "(LOWER(a.nome) LIKE LOWER(concat('%', :keyword, '%')) OR " +
            "LOWER(a.cidade) LIKE LOWER(concat('%', :keyword, '%')))")
    List<Apiario> pesquisarPorUsuarioEKeyword(@Param("usuario") Usuario usuario, @Param("keyword") String keyword);
}