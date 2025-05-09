package com.example.securite.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


@Table(name = "execution_action")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ExecutionAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateEcheance;

    private LocalDate datePrevueRealisation;
    private LocalDate dateReelleRealisation;

    @Column(length = 1000)
    private String commentaire;

    private boolean realisee;

    @ElementCollection
    @CollectionTable(name = "Execution_medias", joinColumns = @JoinColumn(name = "Execution_id"))
    @Column(name = "media_path")
    private List<String> medias;

    @OneToOne
    private PlanAction action;


}

