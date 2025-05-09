package com.example.securite.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "agent_functions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgentFunction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String functionName;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "visite_id", nullable = false)
    private VisiteSecurite visiteSecurite;  // Link to the specific visit


    // Vous pouvez ajouter d'autres champs si nécessaire, par exemple, une description ou un statut.
}
