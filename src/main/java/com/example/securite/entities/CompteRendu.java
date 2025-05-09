package com.example.securite.entities;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "comptes_rendus")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompteRendu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate dateSoumission;

    @ElementCollection
    @CollectionTable(name = "compte_rendu_reponses", joinColumns = @JoinColumn(name = "compte_rendu_id"))
    @Column(name = "reponse")
    private Map<String, String> reponses;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String contenu;

    @ElementCollection
    @CollectionTable(name = "compte_rendu_medias", joinColumns = @JoinColumn(name = "compte_rendu_id"))
    @Column(name = "media_path")
    private List<String> medias;
    private String statut;

    @ManyToOne
    @JoinColumn(name = "visite_id", nullable = false)
    private VisiteSecurite visite;

    @OneToMany(mappedBy = "compteRendu", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PlanAction> plansAction;
}
