package com.example.securite.controllers;

import com.example.securite.entities.APS;
import com.example.securite.entities.Responsable;
import com.example.securite.entities.Utilisateur;
import com.example.securite.entities.Visiteur;
import com.example.securite.services.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import com.example.securite.repositories.UtilisateurRepository;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UtilisateurRepository utilisateurRepository;

    // ✅ Correct constructor injection
    public AuthController(AuthService authService, UtilisateurRepository utilisateurRepository) {
        this.authService = authService;
        this.utilisateurRepository = utilisateurRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, Object> utilisateurMap) {
        String type = (String) utilisateurMap.get("type");
        ObjectMapper mapper = new ObjectMapper();
        Utilisateur utilisateur;

        switch (type) {
            case "VISITEUR":
                utilisateur = mapper.convertValue(utilisateurMap, Visiteur.class);
                break;
            case "APS":
                utilisateur = mapper.convertValue(utilisateurMap, APS.class);
                break;
            case "RESPONSABLE":
                utilisateur = mapper.convertValue(utilisateurMap, Responsable.class);
                break;
            default:
                throw new IllegalArgumentException("Type d'utilisateur inconnu : " + type);
        }

        Utilisateur registeredUser = authService.register(utilisateur);
        return ResponseEntity.ok(registeredUser);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        String token = authService.authenticate(email, password);

        // ✅ Récupérer l'utilisateur depuis la base de données
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé !"));

        // ✅ Déterminer le rôle de l'utilisateur
        String role = utilisateur.getClass().getSimpleName().toUpperCase();

        // ✅ Retourner l'ID utilisateur aussi
        return ResponseEntity.ok(Map.of(
                "token", token,
                "role", role,
                "id", utilisateur.getId()  // ✅ Ajout de l'ID utilisateur dans la réponse
        ));
    }

}
