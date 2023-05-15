package com.gestionSalleCefim.Group3.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "formation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Schema(description = "represents a course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "The unique identifier of the course.")
    private Integer id;

    @Column(name = "nom")
    @Schema(description = "The name of the course.")
    private String name;

    @Column(name = "date_debut")
    @Schema(description = "The start date of the course.", format = "date")
    private LocalDate startDate;

    @Column(name = "date_fin")
    @Schema(description = "The end date of the course.", format = "date")
    private LocalDate endDate;

    @Column(name = "nb_etudiants")
    @Schema(description = "The number of students enrolled in the course.")
    private Integer nbStudents;

    @OneToMany(mappedBy = "course", targetEntity = Reservation.class, cascade = CascadeType.ALL)
    @JsonIdentityReference(alwaysAsId = true)
    private List<Reservation> reservation = new ArrayList<>();
}

