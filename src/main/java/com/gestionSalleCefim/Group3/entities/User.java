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
@Table(name = "utilisateur")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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

    @ManyToOne
    @JoinColumn(name = "roles_id", referencedColumnName = "id", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user", targetEntity = Reservation.class, cascade = CascadeType.ALL)
    @JsonIdentityReference(alwaysAsId = true)
    private List<Reservation> reservations = new ArrayList<>();
}
