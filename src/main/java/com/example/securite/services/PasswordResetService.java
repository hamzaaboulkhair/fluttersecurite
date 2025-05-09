package com.example.securite.services;

import com.example.securite.entities.PasswordResetToken;
import com.example.securite.entities.Utilisateur;
import com.example.securite.repositories.PasswordResetTokenRepository;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordResetService {

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private JavaMailSender mailSender;

    public String sendPasswordResetEmail(String email) {
        Optional<Utilisateur> utilisateurOpt = utilisateurService.getUtilisateurByEmail(email);
        if (utilisateurOpt.isPresent()) {
            Utilisateur utilisateur = utilisateurOpt.get();

            // Générer un token unique
            String token = UUID.randomUUID().toString();

            // Créer un token avec une expiration de 1 heure
            LocalDateTime expirationTime = LocalDateTime.now().plusHours(1);
            PasswordResetToken resetToken = new PasswordResetToken(utilisateur, token, expirationTime);

            // Sauvegarder le token dans la base de données
            passwordResetTokenRepository.save(resetToken);

            // Envoyer l'email avec le lien de réinitialisation
            sendEmail(utilisateur, token);

            return token;  // Return the token
        } else {
            throw new RuntimeException("Utilisateur non trouvé");
        }
    }


    private void sendEmail(Utilisateur utilisateur, String token) {
        // Créez un lien de réinitialisation du mot de passe
        String resetPasswordUrl = "http://localhost:8080/reset-password?token=" + token;

        // Créez et envoyez l'email
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(utilisateur.getEmail());
            helper.setSubject("Réinitialisation de votre mot de passe");
            helper.setText("Cliquez sur le lien suivant pour réinitialiser votre mot de passe : " + resetPasswordUrl);
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'envoi de l'email de réinitialisation");
        }
    }

    public void resetPassword(String token, String newPassword) {
        // Find the reset token by its value
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token invalide"));

        // Check if the token has expired
        if (resetToken.getExpirationTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Le token a expiré.");
        }

        // Get the user associated with the token
        Utilisateur utilisateur = resetToken.getUtilisateur();

        // Update the user's password
        utilisateur.setPassword(newPassword);  // You should hash the password before saving

        // Save the updated user
        utilisateurService.createUtilisateur(utilisateur);

        // Optionally, delete the token after password reset
        passwordResetTokenRepository.delete(resetToken);
    }

}
