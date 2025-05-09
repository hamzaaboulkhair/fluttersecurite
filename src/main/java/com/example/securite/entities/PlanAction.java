package com.example.securite.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "plans_actions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlanAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String action;

    private String responsable;
    private LocalDate date;
    private String type;
    private String correctionImmediate;
    private String statut;
    private String observation;

    @ManyToOne
    @JoinColumn(name = "compte_rendu_id", nullable = false)
    @JsonBackReference
    private CompteRendu compteRendu;



}
