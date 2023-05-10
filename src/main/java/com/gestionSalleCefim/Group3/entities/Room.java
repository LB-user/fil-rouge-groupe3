package com.gestionSalleCefim.Group3.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "salle")
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    public Room(String name) {
        this.name = name;
    }
}
