package com.gestionSalleCefim.Group3.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "batiment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nom")
    private String name;

    @Column(name = "adresse")
    private String address;

    @Column(name = "ville")
    private String city;

    @Column(name = "code_postal")
    private String postalCode;

    @ManyToOne
    @JoinColumn(name = "campus_id", referencedColumnName = "id", nullable = false)
    private Campus campus;

    Building(String name){
        this.name = name;
    }
}
