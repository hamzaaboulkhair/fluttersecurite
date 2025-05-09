package com.example.securite.controllers;

import com.example.securite.services.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;

    // Endpoint pour envoyer l'email avec le token
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        try {
            // Send the password reset email and generate a token
            String token = passwordResetService.sendPasswordResetEmail(email);

            // Respond with the token to use in the frontend
            return ResponseEntity.ok(token);  // Send the generated token as part of the response
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de l'envoi du lien de réinitialisation.");
        }
    }



    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam("token") String token, @RequestParam("newPassword") String newPassword) {
        try {
            // Validate token and reset password
            passwordResetService.resetPassword(token, newPassword);
            return ResponseEntity.ok("Mot de passe réinitialisé avec succès.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Échec de la réinitialisation du mot de passe: " + e.getMessage());
        }
    }







}
