package com.example.securite.entities;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

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

    @Column(nullable = false, columnDefinition = "TEXT")
    private String contenu;

    private String medias;
    private String statut;

    @ManyToOne
    @JoinColumn(name = "visite_id", nullable = false)
    private VisiteSecurite visite;

    @OneToMany(mappedBy = "compteRendu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlanAction> plansAction;
}
