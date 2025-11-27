package com.appmelinabit.model;

import jakarta.persistence.*;
import java.time.LocalDateTime; // <--- ADICIONE ESTA LINHA
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "password_reset_token")
public class PasswordResetToken {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @OneToOne(targetEntity = Usuario.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private Usuario usuario;

    @Column(nullable = false)
    private LocalDateTime expiryDate;

    // Construtor
    public PasswordResetToken() {}

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }
}