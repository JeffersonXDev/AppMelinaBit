package com.appmelinabit.repository;

import com.appmelinabit.model.Apiario;
import com.appmelinabit.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApiarioRepository extends JpaRepository<Apiario, Long> { 
    

    List<Apiario> findByUsuario(Usuario usuario);

    List<Apiario> findByUsuarioIdUsuario(Long idUsuario);

    Optional<Apiario> findByIdApiarioAndUsuarioIdUsuario(Long idApiario, Long idUsuario);
}