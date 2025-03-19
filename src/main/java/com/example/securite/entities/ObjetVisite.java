package com.example.securite.entities;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "objets_visites")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ObjetVisite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String localisation;
    private String fluide;
    private String natureTravaux;
    private String service;
    private String departement;

    private double latitude;
    private double longitude;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "aps_id")
    private APS aps;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "responsable_id")
    private Responsable responsable;

    @JsonIgnore
    @OneToMany(mappedBy = "objetVisite", cascade = CascadeType.ALL)
    private List<VisiteSecurite> visites = new ArrayList<>();
}

