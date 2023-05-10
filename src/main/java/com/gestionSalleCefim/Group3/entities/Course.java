package com.gestionSalleCefim.Group3.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "formation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nom")
    private String name;
    @Column(name = "date_debut")
    private LocalDate startTime;
    @Column(name = "date_fin")
    private LocalDate endTime;
    @Column(name = "nb_etudiants")
    private Integer nbStudents;

    Course(String lastname){
        this.name = lastname;
    }
}
