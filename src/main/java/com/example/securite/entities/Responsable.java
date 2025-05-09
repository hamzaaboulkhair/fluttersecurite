package com.example.securite.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("RESPONSABLE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Responsable extends Utilisateur {
    @JsonIgnore
    @OneToMany(mappedBy = "responsable", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participation> participations;
    @JsonIgnore
    @OneToMany(mappedBy = "responsable")
    private List<ObjetVisite> objetsVisites = new ArrayList<>();
}

