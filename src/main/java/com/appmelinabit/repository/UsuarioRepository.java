package com.appmelinabit.repository;

import com.appmelinabit.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca um Usuário pelo seu endereço de e-mail.
     * Este é um método crucial para autenticação e segurança,
     * e é chamado pelo UsuarioService.
     * O Spring Data JPA implementa este método automaticamente (query method).
     * @param email O e-mail do usuário.
     * @return Um Optional contendo o Usuário, se encontrado.
     */
    Optional<Usuario> findByEmail(String email);
    
    // O JpaRepository já fornece: save(), findAll(), findById(), delete(), etc.
}