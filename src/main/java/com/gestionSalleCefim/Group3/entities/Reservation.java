package com.gestionSalleCefim.Group3.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reservation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "titre", nullable = false, length = 50)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "date_debut", nullable = false)
    private LocalDate startDate;

    @Column(name = "date_fin", nullable = false)
    private LocalDate endDate;

    @Column(name = "heure_debut")
    private LocalTime startHour;

    @Column(name = "heure_fin")
    private LocalTime endHour;

    // @ManyToOne
    // @JoinColumn(name = "formation_id", nullable = false)
    // private Formation formation;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id", nullable = false)
    private User User;

    // @ManyToOne
    // @JoinColumn(name = "salle_id", nullable = false)
    // private Salle salle;
}
