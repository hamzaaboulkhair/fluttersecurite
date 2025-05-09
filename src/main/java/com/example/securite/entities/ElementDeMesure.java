package com.example.securite.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "element_de_mesure")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ElementDeMesure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @JsonProperty("elementEfficacite") // pour l’API
    private String elementEfficacite;

    @JsonProperty("detailsEfficacite")
    private String detailsEfficacite;

    @JsonProperty("resultat")
    private String resultat;

    @JsonProperty("note")
    private int note;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "PlanAction_id", nullable = false)
    private PlanAction planAction;


}