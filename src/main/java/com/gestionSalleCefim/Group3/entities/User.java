package com.gestionSalleCefim.Group3.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "utilisateur")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nom")
    private String lastName;
    @Column(name = "prenom")
    private String firstName;
    @Column(name = "email")
    private String email;
    @Column(name = "mot_de_passe")
    private String password;

    User(String lastname){
        this.lastName = lastname;
    }
}
