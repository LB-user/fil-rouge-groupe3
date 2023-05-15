package com.gestionSalleCefim.Group3.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Represents a building in the campus")
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "The unique identifier of the building")
    private Integer id;

    @Column(name = "nom")
    @Schema(description = "The name of the building")
    private String name;

    @Column(name = "adresse")
    @Schema(description = "The address of the building")
    private String address;

    @Column(name = "ville")
    @Schema(description = "The city where the building is located")
    private String city;

    @Column(name = "code_postal")
    @Schema(description = "The postal code of the building's location")
    private String postalCode;

    @ManyToOne
    @JoinColumn(name = "campus_id", referencedColumnName = "id", nullable = false)
    @Schema(description = "The campus to which the building belongs")
    @JsonIdentityReference(alwaysAsId = true)
    private Campus campus;

    @OneToMany(mappedBy = "building", targetEntity = Room.class, cascade = CascadeType.ALL)
    @Schema(description = "The rooms that are located in this building")
    private List<Room> rooms = new ArrayList<>();
}

