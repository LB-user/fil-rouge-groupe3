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
@Table(name = "utilisateur")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Schema(description = "Represents a user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "The unique identifier of the user")
    private Integer id;

    @Column(name = "nom")
    @Schema(description = "The last name of the user")
    private String lastName;

    @Column(name = "prenom")
    @Schema(description = "The first name of the user")
    private String firstName;

    @Column(name = "email")
    @Schema(description = "The email address of the user")
    private String email;

    @Column(name = "mot_de_passe")
    @Schema(description = "The password of the user")
    private String password;

    @ManyToOne
    @JoinColumn(name = "roles_id", referencedColumnName = "id", nullable = false)
    @JsonIdentityReference(alwaysAsId = true)
    @Schema(description = "The role of the user")
    private Role role;

    @OneToMany(mappedBy = "user", targetEntity = Reservation.class, cascade = CascadeType.ALL)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @Schema(description = "The list of reservations made by the user")
    private List<Reservation> reservations = new ArrayList<>();
}
