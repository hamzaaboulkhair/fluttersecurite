package com.example.securite.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@DiscriminatorValue("VISITEUR")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Visiteur extends Utilisateur {

    @Column(name = "matricule")
    private String matricule;

    @Column(name = "fonction")
    private String fonction;

    @Column(name = "entite")
    private String entite;

    @Column(name = "service")
    private String service;


    @JsonIgnore
    @OneToMany(mappedBy = "visiteur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participation> participations;
}
