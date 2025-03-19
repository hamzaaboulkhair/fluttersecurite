package com.example.securite.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "observations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Observation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String observation;
    private String type;
    private String action;
    private String correction;

    @ManyToOne
    @JoinColumn(name = "visite_id", nullable = false)
    private VisiteSecurite visite;
}

