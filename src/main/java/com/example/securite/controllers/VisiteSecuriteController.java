package com.example.securite.controllers;

import com.example.securite.entities.*;
import com.example.securite.services.ObjetVisiteService;
import com.example.securite.services.ParticipationService;
import com.example.securite.services.UtilisateurService;
import com.example.securite.services.VisiteSecuriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/visites")
public class VisiteSecuriteController {

    @Autowired
    private ParticipationService participationService;

    @Autowired
    private VisiteSecuriteService visiteSecuriteService;

    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private ObjetVisiteService objetVisiteService;

    @GetMapping
    public List<VisiteSecurite> getAllVisites() {
        return visiteSecuriteService.getAllVisites();
    }

    @GetMapping("/{id}")
    public Optional<VisiteSecurite> getVisiteById(@PathVariable Long id) {
        return visiteSecuriteService.getVisiteById(id);
    }

    @GetMapping("/aps/{apsId}")
    public ResponseEntity<?> getVisitesForAps(@PathVariable Long apsId) {
        try {
            List<VisiteSecurite> visites = visiteSecuriteService.getVisitesByAps(apsId);

            if (visites.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            // Conversion en DTO
            List<Map<String, Object>> response = visites.stream()
                    .map(visite -> {
                        Map<String, Object> visiteMap = new HashMap<>();
                        visiteMap.put("id", visite.getId());
                        visiteMap.put("date", visite.getDate());
                        visiteMap.put("heureDebut", visite.getHeureDebut());
                        visiteMap.put("heureFin", visite.getHeureFin());
                        visiteMap.put("etat", visite.getEtat());

                        // Objet de visite
                        if (visite.getObjetVisite() != null) {
                            Map<String, Object> objetMap = new HashMap<>();
                            objetMap.put("id", visite.getObjetVisite().getId());
                            objetMap.put("localisation", visite.getObjetVisite().getLocalisation());
                            visiteMap.put("objetVisite", objetMap);
                        }

                        // Participants
                        List<Map<String, String>> participants = visite.getParticipants().stream()
                                .map(p -> {
                                    Map<String, String> participant = new HashMap<>();
                                    if (p.getVisiteur() != null) {
                                        participant.put("type", "VISITEUR");
                                        participant.put("nom", p.getVisiteur().getNom());
                                    } else if (p.getAps() != null) {
                                        participant.put("type", "APS");
                                        participant.put("nom", p.getAps().getNom());
                                    } else if (p.getResponsable() != null) {
                                        participant.put("type", "RESPONSABLE");
                                        participant.put("nom", p.getResponsable().getNom());
                                    }
                                    return participant;
                                })
                                .collect(Collectors.toList());

                        visiteMap.put("participants", participants);
                        return visiteMap;
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la récupération des visites: " + e.getMessage());
        }
    }

    @GetMapping("/responsable/{responsableId}")
    public ResponseEntity<?> getVisitesForResponsable(@PathVariable Long responsableId) {
        try {
            List<VisiteSecurite> visites = visiteSecuriteService.getVisitesByResponsable(responsableId);

            if (visites.isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            List<Map<String, Object>> response = visites.stream()
                    .map(visite -> {
                        Map<String, Object> visiteMap = new HashMap<>();
                        visiteMap.put("id", visite.getId());
                        visiteMap.put("date", visite.getDate());
                        visiteMap.put("heureDebut", visite.getHeureDebut());
                        visiteMap.put("heureFin", visite.getHeureFin());
                        visiteMap.put("etat", visite.getEtat());

                        if (visite.getObjetVisite() != null) {
                            Map<String, Object> objetMap = new HashMap<>();
                            objetMap.put("id", visite.getObjetVisite().getId());
                            objetMap.put("localisation", visite.getObjetVisite().getLocalisation());
                            visiteMap.put("objetVisite", objetMap);
                        }

                        List<Map<String, String>> participants = visite.getParticipants().stream()
                                .map(p -> {
                                    Map<String, String> participant = new HashMap<>();
                                    if (p.getVisiteur() != null) {
                                        participant.put("type", "VISITEUR");
                                        participant.put("nom", p.getVisiteur().getNom());
                                    } else if (p.getAps() != null) {
                                        participant.put("type", "APS");
                                        participant.put("nom", p.getAps().getNom());
                                    } else if (p.getResponsable() != null) {
                                        participant.put("type", "RESPONSABLE");
                                        participant.put("nom", p.getResponsable().getNom());
                                    }
                                    return participant;
                                })
                                .collect(Collectors.toList());

                        visiteMap.put("participants", participants);
                        return visiteMap;
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la récupération des visites: " + e.getMessage());
        }
    }


    @GetMapping("/utilisateur/{id}")
    public ResponseEntity<List<Map<String, Object>>> getVisitesByUtilisateur(@PathVariable Long id) {
        Utilisateur utilisateur = utilisateurService.getUtilisateurById(id).orElse(null);

        if (utilisateur == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<VisiteSecurite> visites = visiteSecuriteService.getVisitesByUtilisateur(utilisateur);

        // ✅ Transformer les visites en une liste de Maps pour inclure tout objetVisite + Participants
        List<Map<String, Object>> visitesAvecDetails = visites.stream().map(visite -> {
            Map<String, Object> visiteMap = new HashMap<>();
            visiteMap.put("id", visite.getId());
            visiteMap.put("date", visite.getDate());
            visiteMap.put("heureDebut", visite.getHeureDebut());
            visiteMap.put("heureFin", visite.getHeureFin());
            visiteMap.put("etat", visite.getEtat());
            visiteMap.put("localisation", visite.getObjetVisite().getLocalisation());

            if (visite.getObjetVisite() != null) {
                Map<String, Object> objetVisiteMap = new HashMap<>();
                objetVisiteMap.put("id", visite.getObjetVisite().getId());
                objetVisiteMap.put("localisation", visite.getObjetVisite().getLocalisation());
                objetVisiteMap.put("fluide", visite.getObjetVisite().getFluide());
                objetVisiteMap.put("natureTravaux", visite.getObjetVisite().getNatureTravaux());
                objetVisiteMap.put("service", visite.getObjetVisite().getService());
                objetVisiteMap.put("departement", visite.getObjetVisite().getDepartement());

                if (visite.getObjetVisite().getAps() != null) {
                    Map<String, Object> apsMap = new HashMap<>();
                    apsMap.put("id", visite.getObjetVisite().getAps().getId());
                    apsMap.put("nom", visite.getObjetVisite().getAps().getNom());
                    apsMap.put("email", visite.getObjetVisite().getAps().getEmail());
                    apsMap.put("telephone", visite.getObjetVisite().getAps().getTelephone());
                    objetVisiteMap.put("aps", apsMap);
                }

                if (visite.getObjetVisite().getResponsable() != null) {
                    Map<String, Object> responsableMap = new HashMap<>();
                    responsableMap.put("id", visite.getObjetVisite().getResponsable().getId());
                    responsableMap.put("nom", visite.getObjetVisite().getResponsable().getNom());
                    responsableMap.put("email", visite.getObjetVisite().getResponsable().getEmail());
                    responsableMap.put("telephone", visite.getObjetVisite().getResponsable().getTelephone());
                    objetVisiteMap.put("responsable", responsableMap);
                }

                visiteMap.put("objetVisite", objetVisiteMap);
            }

            // ✅ Récupération des participants
            List<Map<String, Object>> participantsList = new ArrayList<>();
            for (Participation participation : visite.getParticipants()) {
                Map<String, Object> participantMap = new HashMap<>();

                if (participation.getVisiteur() != null) {
                    participantMap.put("id", participation.getVisiteur().getId());
                    participantMap.put("nom", participation.getVisiteur().getNom());
                    participantMap.put("prenom", participation.getVisiteur().getPrenom());
                    participantMap.put("email", participation.getVisiteur().getEmail());
                    participantMap.put("telephone", participation.getVisiteur().getTelephone());
                    participantMap.put("type", "Visiteur");
                } else if (participation.getAps() != null) {
                    participantMap.put("id", participation.getAps().getId());
                    participantMap.put("nom", participation.getAps().getNom());
                    participantMap.put("prenom", participation.getAps().getPrenom());
                    participantMap.put("email", participation.getAps().getEmail());
                    participantMap.put("telephone", participation.getAps().getTelephone());
                    participantMap.put("type", "APS");
                } else if (participation.getResponsable() != null) {
                    participantMap.put("id", participation.getResponsable().getId());
                    participantMap.put("nom", participation.getResponsable().getNom());
                    participantMap.put("prenom", participation.getResponsable().getPrenom());
                    participantMap.put("email", participation.getResponsable().getEmail());
                    participantMap.put("telephone", participation.getResponsable().getTelephone());
                    participantMap.put("type", "Responsable");
                }

                participantsList.add(participantMap);
            }

            visiteMap.put("participants", participantsList);
            return visiteMap;
        }).toList();

        return ResponseEntity.ok(visitesAvecDetails);
    }


    @GetMapping("/visite/{id}/details")
    public ResponseEntity<Map<String, Object>> getVisiteDetails(@PathVariable Long id) {
        VisiteSecurite visite = visiteSecuriteService.getVisiteByIdWithParticipants(id).orElse(null);

        if (visite == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Map<String, Object> visiteMap = new HashMap<>();
        visiteMap.put("id", visite.getId());
        visiteMap.put("date", visite.getDate());
        visiteMap.put("heureDebut", visite.getHeureDebut());
        visiteMap.put("heureFin", visite.getHeureFin());
        visiteMap.put("etat", visite.getEtat());

        // ✅ Retrieve participants
        List<Map<String, Object>> participantsList = new ArrayList<>();
        for (Participation participation : visite.getParticipants()) {
            Map<String, Object> participantMap = new HashMap<>();
            if (participation.getVisiteur() != null) {
                participantMap.put("id", participation.getVisiteur().getId());
                participantMap.put("nom", participation.getVisiteur().getNom());
                participantMap.put("prenom", participation.getVisiteur().getPrenom());
            } else if (participation.getAps() != null) {
                participantMap.put("id", participation.getAps().getId());
                participantMap.put("nom", participation.getAps().getNom());
                participantMap.put("prenom", participation.getAps().getPrenom());
            } else if (participation.getResponsable() != null) {
                participantMap.put("id", participation.getResponsable().getId());
                participantMap.put("nom", participation.getResponsable().getNom());
                participantMap.put("prenom", participation.getResponsable().getPrenom());
            }

            participantsList.add(participantMap);
        }

        System.out.println(" Participants pour la visite ID " + id + " : " + participantsList);
        visiteMap.put("participants", participantsList);

        return ResponseEntity.ok(visiteMap);
    }




    @PostMapping("/ajouter")
    public ResponseEntity<?> ajouterVisite(@RequestBody Map<String, Object> requestBody) {
        System.out.println("🔹 Requête reçue : " + requestBody);

        Long utilisateurId = (requestBody.containsKey("utilisateurId") && requestBody.get("utilisateurId") != null)
                ? ((Number) requestBody.get("utilisateurId")).longValue()
                : null;

        if (utilisateurId == null) {
            return ResponseEntity.badRequest().body("Erreur : utilisateurId est requis.");
        }

        String role = (String) requestBody.get("role");

        Long objetVisiteId = (requestBody.containsKey("objetVisite") && requestBody.get("objetVisite") != null)
                ? ((Number) ((Map<String, Object>) requestBody.get("objetVisite")).get("id")).longValue()
                : null;

        if (objetVisiteId == null) {
            return ResponseEntity.badRequest().body("Erreur : objetVisite.id est requis.");
        }

        VisiteSecurite visite = new VisiteSecurite();
        visite.setDate(LocalDate.parse((String) requestBody.get("date")));
        visite.setHeureDebut(LocalTime.parse((String) requestBody.get("heureDebut")));
        visite.setHeureFin(LocalTime.parse((String) requestBody.get("heureFin")));
        visite.setEtat((String) requestBody.get("etat"));

        ObjetVisite objetVisite = new ObjetVisite();
        objetVisite.setId(objetVisiteId);
        visite.setObjetVisite(objetVisite);

        VisiteSecurite savedVisite = visiteSecuriteService.createVisite(visite, utilisateurId, role);

        // ✅ Ajouter l'utilisateur actuel comme participant
        Utilisateur utilisateurActuel = utilisateurService.getUtilisateurById(utilisateurId).orElse(null);
        if (utilisateurActuel != null) {
            Participation participation = new Participation();
            participation.setVisite(savedVisite);

            if (utilisateurActuel instanceof Visiteur) {
                participation.setVisiteur((Visiteur) utilisateurActuel);
            } else if (utilisateurActuel instanceof APS) {
                participation.setAps((APS) utilisateurActuel);
            } else if (utilisateurActuel instanceof Responsable) {
                participation.setResponsable((Responsable) utilisateurActuel);
            }

            participationService.createParticipation(participation);
            System.out.println("✅ Participation enregistrée pour l'utilisateur " + utilisateurActuel.getNom());
        } else {
            System.out.println("⚠️ Aucun utilisateur trouvé pour l'utilisateurId : " + utilisateurId);
        }

        // ✅ Ajouter les autres participants envoyés dans la requête
        if (requestBody.containsKey("participants")) {
            List<Map<String, Object>> participantsData = (List<Map<String, Object>>) requestBody.get("participants");
            for (Map<String, Object> participantMap : participantsData) {
                String nomParticipant = (String) participantMap.get("nom");

                // Now handle the `utilisateurs` list correctly by using `getUtilisateurByUsername`
                Optional<Utilisateur> utilisateurs = utilisateurService.getUtilisateurByUsername(nomParticipant);

                if (utilisateurs != null && !utilisateurs.isEmpty()) {
                    Utilisateur utilisateur = utilisateurs.get(); // Get the first user, assuming they are unique now
                    Participation participation = new Participation();
                    participation.setVisite(savedVisite);

                    if (utilisateur instanceof Visiteur) {
                        participation.setVisiteur((Visiteur) utilisateur);
                    } else if (utilisateur instanceof APS) {
                        participation.setAps((APS) utilisateur);
                    } else if (utilisateur instanceof Responsable) {
                        participation.setResponsable((Responsable) utilisateur);
                    }

                    participationService.createParticipation(participation);
                    System.out.println("✅ Participation ajoutée pour " + nomParticipant);
                } else {
                    System.out.println("⚠️ Participant non trouvé ou non unique : " + nomParticipant);
                }
            }
        }

        return ResponseEntity.ok(savedVisite);
    }








    @DeleteMapping("/{id}")
    public void deleteVisite(@PathVariable Long id) {
        visiteSecuriteService.deleteVisite(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VisiteSecurite> modifierVisite(@PathVariable Long id, @RequestBody Map<String, Object> updatedVisiteData) {
        return visiteSecuriteService.getVisiteById(id)
                .map(visite -> {
                    // Update visit data
                    visite.setDate(LocalDate.parse((String) updatedVisiteData.get("date")));
                    visite.setHeureDebut(LocalTime.parse((String) updatedVisiteData.get("heureDebut")));
                    visite.setHeureFin(LocalTime.parse((String) updatedVisiteData.get("heureFin")));
                    visite.setEtat((String) updatedVisiteData.get("etat"));

                    // Update objetVisite if new ID is provided
                    if (updatedVisiteData.containsKey("objetVisite")) {
                        Map<String, Object> objetVisiteData = (Map<String, Object>) updatedVisiteData.get("objetVisite");
                        Long objetVisiteId = ((Number) objetVisiteData.get("id")).longValue();

                        ObjetVisite objetVisite = objetVisiteService.getObjetVisiteById(objetVisiteId)
                                .orElseThrow(() -> new RuntimeException("Objet de visite non trouvé"));
                        visite.setObjetVisite(objetVisite);
                    }

                    // Update participants
                    if (updatedVisiteData.containsKey("participants")) {
                        List<Map<String, Object>> participantsData = (List<Map<String, Object>>) updatedVisiteData.get("participants");

                        // 1️⃣ Remove old participants


                        // 2️⃣ Add new participants
                        for (Map<String, Object> participantMap : participantsData) {
                            String nomParticipant = (String) participantMap.get("nom");
                            Optional<Utilisateur> utilisateurs = utilisateurService.getUtilisateurByUsername(nomParticipant);

                            if (utilisateurs != null && !utilisateurs.isEmpty()) {
                                Utilisateur utilisateur = utilisateurs.get();

                                Participation participation = new Participation();
                                participation.setVisite(visite);

                                if (utilisateur instanceof Visiteur) {
                                    participation.setVisiteur((Visiteur) utilisateur);
                                } else if (utilisateur instanceof APS) {
                                    participation.setAps((APS) utilisateur);
                                } else if (utilisateur instanceof Responsable) {
                                    participation.setResponsable((Responsable) utilisateur);
                                }

                                participationService.createParticipation(participation);
                                System.out.println("✅ Participation ajoutée pour " + nomParticipant);
                            } else {
                                System.out.println("⚠️ Participant non trouvé ou non unique : " + nomParticipant);
                            }
                        }
                    }

                    // Save the updated visit
                    visiteSecuriteService.updateVisite(id, visite);
                    return ResponseEntity.ok(visite);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }




}




