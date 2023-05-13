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
@Table(name = "batiment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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

    @OneToMany(mappedBy = "building", targetEntity = Room.class, cascade = CascadeType.ALL)
    @JsonIdentityReference(alwaysAsId = true)
    private List<Room> rooms = new ArrayList<>();
}
