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

    // Encontra o token pelo seu UUID (usado para validar o link de redefinição)
    Optional<PasswordResetToken> findByToken(String token);

    // Encontra o token pelo objeto Usuario (ainda útil para verificações)
    Optional<PasswordResetToken> findByUsuario(Usuario usuario);
    
    // CORREÇÃO FINAL: Usa @Modifying e HQL/JPQL para garantir que o DELETE seja executado.
    // Isso resolve o erro de chave duplicada de forma permanente.
    @Modifying
    @Query("DELETE FROM PasswordResetToken t WHERE t.usuario = :usuario")
    void deleteByUsuario(Usuario usuario); 
}