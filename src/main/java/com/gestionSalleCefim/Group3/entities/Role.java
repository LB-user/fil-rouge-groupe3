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
@Table(name = "role")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Schema(description = "Represents a role in the system.")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Schema(description = "The unique identifier of the role.")
    private Integer id;

    @Column(name = "nom")
    @Schema(description = "The name of the role.")
    private String name;

    @OneToMany(mappedBy = "role", targetEntity = User.class, cascade = CascadeType.ALL)
    @JsonIdentityReference(alwaysAsId = true)
    @Schema(description = "The list of users that have this role.")
    private List<User> users = new ArrayList<>();
}

