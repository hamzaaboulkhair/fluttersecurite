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

    private String matricule;
    private String fonction;
    private String entite;
    private String service;


    @JsonIgnore
    @OneToMany(mappedBy = "visiteur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participation> participations;
}
