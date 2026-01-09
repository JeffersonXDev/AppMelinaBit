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

    // Lista apenas os apiários do usuário logado (usado na tela de listagem)
    List<Apiario> findByUsuario(Usuario usuario);

    // Conta quantos apiários o usuário tem (para o widget do Dashboard)
    long countByUsuario(Usuario usuario);

    // Soma a quantidade total de colmeias de todos os apiários do usuário
    @Query("SELECT SUM(a.qntdColmeias) FROM Apiario a WHERE a.usuario = :usuario")
    Integer sumColmeiasByUsuario(@Param("usuario") Usuario usuario);
}