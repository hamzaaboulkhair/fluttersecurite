package com.example.securite.services;

import com.example.securite.entities.Utilisateur;
import com.example.securite.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    public Optional<Utilisateur> getUtilisateurById(Long id) {
        return utilisateurRepository.findById(id);
    }

    public Optional<Utilisateur> getUtilisateurByUsername(String username) {
        List<Utilisateur> utilisateurs = utilisateurRepository.findByNom(username);

        if (utilisateurs.isEmpty()) {
            return Optional.empty();
        }

        // Prendre le premier utilisateur trouvé (vous pourriez ajouter une logique de sélection plus sophistiquée ici)
        return Optional.of(utilisateurs.get(0));
    }


    public  Optional<Utilisateur> getUtilisateurByEmail(String email) {
        return utilisateurRepository.findByEmail(email);
    }

    public Utilisateur createUtilisateur(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    public void deleteUtilisateur(Long id) {
        utilisateurRepository.deleteById(id);
    }


}
