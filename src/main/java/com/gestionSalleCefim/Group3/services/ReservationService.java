package com.gestionSalleCefim.Group3.services;

import com.gestionSalleCefim.Group3.entities.Reservation;
import org.springframework.stereotype.Service;

@Service
public class ReservationService extends BaseService<Reservation>{
   /* @Autowired
    private ReservationRepository reservationRepository;


    public Reservation saveReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation getReservationById(Integer id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid reservation ID: " + id));
    }

    public List<Reservation> getReservationsByTitre(String titre) {
        return reservationRepository.findByTitre(titre);
    }

    public List<Reservation> getReservationsByDateDebut(LocalDate dateDebut) {
        return reservationRepository.findByDateDebut(dateDebut);
    }

    public List<Reservation> getReservationsByDateFin(LocalDate dateFin) {
        return reservationRepository.findByDateFin(dateFin);
    }
    /*
    public List<Reservation> getReservationsByFormationId(Integer formationId) {
        return reservationRepository.findByFormationId(formationId);
    }

     */

   /*  public List<Reservation> getReservationsByUtilisateurId(Integer utilisateurId) {
        return reservationRepository.findByUtilisateurId(utilisateurId);
    }
    /*
    public List<Reservation> getReservationsBySalleId(Integer salleId) {
        return reservationRepository.findBySalleId(salleId);
    }

     */

    /* public Reservation createReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public Reservation updateReservation(Integer id, Reservation updatedReservation) {
        Reservation reservation = getReservationById(id);
        reservation.setTitre(updatedReservation.getTitre());
        reservation.setDescription(updatedReservation.getDescription());
        reservation.setDateDebut(updatedReservation.getDateDebut());
        reservation.setDateFin(updatedReservation.getDateFin());
        reservation.setHeureDebut(updatedReservation.getHeureDebut());
        reservation.setHeureFin(updatedReservation.getHeureFin());
       // reservation.setFormation(updatedReservation.getFormation());
        reservation.setUtilisateur(updatedReservation.getUtilisateur());
        // reservation.setSalle(updatedReservation.getSalle());

        return reservationRepository.save(reservation);
    }

    public void deleteReservation(Integer id) {
        Reservation reservation = getReservationById(id);
        reservationRepository.delete(reservation);
    } */
}
