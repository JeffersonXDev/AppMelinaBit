package com.appmelinabit.repository;

import com.appmelinabit.model.Apiario;
import com.appmelinabit.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApiarioRepository extends JpaRepository<Apiario, Long> { 
    
    /**
     * Busca todos os Apiários associados a um determinado Usuário.
     * Este método é usado pelo ApiarioService para filtrar por usuário logado.
     */
    List<Apiario> findByUsuario(Usuario usuario);

    /**
     * Busca todos os apiários associados ao ID do usuário.
     * Útil quando temos apenas o ID em mãos.
     */
    List<Apiario> findByUsuarioIdUsuario(Long idUsuario);

    /**
     * Busca um apiário por ID e garante que ele pertence ao usuário logado.
     * Isso resolve o problema de segurança no ManejoService.
     */
    Optional<Apiario> findByIdApiarioAndUsuarioIdUsuario(Long idApiario, Long idUsuario);
}