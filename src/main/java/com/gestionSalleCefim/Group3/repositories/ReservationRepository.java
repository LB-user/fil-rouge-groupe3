package com.gestionSalleCefim.Group3.repositories;

import com.gestionSalleCefim.Group3.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findByTitre(String titre);

    List<Reservation> findByDateDebut(LocalDate dateDebut);

    List<Reservation> findByDateFin(LocalDate dateFin);

    //List<Reservation> findByFormationId(Integer formationId);

    List<Reservation> findByUtilisateurId(Integer utilisateurId);

    //List<Reservation> findBySalleId(Integer salleId);

}
