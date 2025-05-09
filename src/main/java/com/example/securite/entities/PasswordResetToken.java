package com.example.securite.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "password_reset_tokens")
@Getter
@Setter
@AllArgsConstructor
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Utilisateur utilisateur; // Lien avec l'utilisateur

    @Column(nullable = false, unique = true)
    private String token; // Le token de réinitialisation

    @Column(nullable = false)
    private LocalDateTime expirationTime; // Date d'expiration du token

    public PasswordResetToken() {}

    public PasswordResetToken(Utilisateur utilisateur, String token, LocalDateTime expirationTime) {
        this.utilisateur = utilisateur;
        this.token = token;
        this.expirationTime = expirationTime;
    }

    // Getters and Setters
}
