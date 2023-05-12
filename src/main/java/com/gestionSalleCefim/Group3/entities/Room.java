package com.gestionSalleCefim.Group3.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "salle")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nom")
    private String name;
    @Column(name = "capacite")
    private Integer capacity;
    @Column(name = "emplacement")
    private String location;
    @Column(name = "equipement")
    private String equipment;

    @ManyToOne
    @JoinColumn(name = "batiment_id", referencedColumnName = "id", nullable = false)
    private Building building;

    @OneToMany(mappedBy = "room", targetEntity = Reservation.class)
    @JsonIdentityReference(alwaysAsId = true)
    private List<Reservation> reservations = new ArrayList<>();
}


