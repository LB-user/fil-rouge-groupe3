package com.gestionSalleCefim.Group3.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "The Room entity")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "The unique identifier of the room", example = "1")
    private Integer id;

    @Column(name = "nom")
    @Schema(description = "The name of the room", example = "Room A")
    private String name;

    @Column(name = "capacite")
    @Schema(description = "The capacity of the room", example = "50")
    private Integer capacity;

    @Column(name = "emplacement")
    @Schema(description = "The location of the room", example = "Building A, Floor 1")
    private String location;

    @Column(name = "equipement")
    @Schema(description = "The equipment available in the room", example = "Projector, Whiteboard")
    private String equipment;

    @ManyToOne
    @JoinColumn(name = "batiment_id", referencedColumnName = "id", nullable = false)
    @JsonIgnoreProperties("rooms")
    @Schema(description = "The building this room belongs to")
    private Building building;

    @OneToMany(mappedBy = "room", targetEntity = Reservation.class, cascade = CascadeType.ALL)
    @JsonIdentityReference(alwaysAsId = true)
    @Schema(description = "The reservations made for this room")
    private List<Reservation> reservations = new ArrayList<>();
}