package com.appmelinabit.repository;

import com.appmelinabit.model.PasswordResetToken;
import com.appmelinabit.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying; // NOVO IMPORT
import org.springframework.data.jpa.repository.Query;    // NOVO IMPORT
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken> findByToken(String token);

    Optional<PasswordResetToken> findByUsuario(Usuario usuario);

    @Modifying
    @Query("DELETE FROM PasswordResetToken t WHERE t.usuario = :usuario")
    void deleteByUsuario(Usuario usuario); 
}