package com.gestionSalleCefim.Group3.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "reservation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "represents an event reservation of a room")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "The unique identifier of the reservation.")
    private Integer id;

    @Column(name = "titre", nullable = false, length = 50)
    @Schema(description = "The title of the reservation.", required = true)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    @Schema(description = "The description of the reservation.")
    private String description;

    @Column(name = "date_debut", nullable = false)
    @Schema(description = "The start date of the reservation.", required = true)
    private LocalDate startDate;

    @Column(name = "date_fin", nullable = false)
    @Schema(description = "The end date of the reservation.", required = true)
    private LocalDate endDate;

    @Column(name = "heure_debut")
    @Schema(description = "The start time of the reservation.")
    private LocalTime startHour;

    @Column(name = "heure_fin")
    @Schema(description = "The end time of the reservation.")
    private LocalTime endHour;

    @ManyToOne
    @JoinColumn(name = "formation_id", referencedColumnName = "id", nullable = false)
    @Schema(description = "The course associated with the reservation.", required = true)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id", referencedColumnName = "id", nullable = false)
    @Schema(description = "The user who made the reservation.", required = true)
    private User user;

    @ManyToOne
    @JoinColumn(name = "salle_id", referencedColumnName = "id", nullable = false)
    @Schema(description = "The room reserved for the course.", required = true)
    private Room room;
}