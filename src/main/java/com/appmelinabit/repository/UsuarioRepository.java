package com.appmelinabit.repository;

import com.appmelinabit.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> { // Usando Integer aqui!

    // O DashboardController usa isso para achar o usuário logado
    Optional<Usuario> findByEmail(String email);
}