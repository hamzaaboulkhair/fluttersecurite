package com.example.securite.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("APS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class APS extends Utilisateur {
    @JsonIgnore
    @OneToMany(mappedBy = "aps", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participation> participations = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "aps")
    private List<ObjetVisite> objetsVisites = new ArrayList<>();
}
