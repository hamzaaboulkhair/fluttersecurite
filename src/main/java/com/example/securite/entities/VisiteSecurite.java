package com.example.securite.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "visites_securite")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class VisiteSecurite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime heureDebut;

    @Column(nullable = false)
    private LocalTime heureFin;

    @Column(nullable = false)
    private String etat;

    @JsonIgnore
    @OneToMany(mappedBy = "visite", cascade = CascadeType.ALL)
    private List<CompteRendu> comptesRendus = new ArrayList<>();

    @OneToMany(mappedBy = "visite", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Participation> participants = new ArrayList<>();


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "objet_visite_id")
    private ObjetVisite objetVisite;

    @OneToMany(mappedBy = "visiteSecurite", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AgentFunction> agentFunctions; // List of agent functions for this visi


}
