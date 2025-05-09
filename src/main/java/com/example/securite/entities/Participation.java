package com.example.securite.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "participations", uniqueConstraints = {
        @UniqueConstraint(
                name = "participation_unique_constraint",
                columnNames = {"visite_id", "visiteur_id", "aps_id", "responsable_id"}
        )
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Participation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "visiteur_id")
    private Visiteur visiteur;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "aps_id")
    private APS aps;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "responsable_id")
    private Responsable responsable;

    @ManyToOne
    @JoinColumn(name = "visite_id", nullable = false)
    @JsonBackReference
    private VisiteSecurite visite;
}