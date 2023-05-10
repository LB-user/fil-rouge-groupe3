package com.gestionSalleCefim.Group3;


import com.gestionSalleCefim.Group3.entities.Reservation;

import com.gestionSalleCefim.Group3.services.ReservationService;
import jakarta.transaction.Transactional;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;



import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ReservationTests {

    @Autowired
    private ReservationService reservationService;


    @Test
    void testGetAllReservations() throws Exception {
        List<Reservation> reservations = reservationService.getAllReservations();
        reservations.forEach(reservation -> System.out.println(reservation.getDescription()));
    }

    @Test
    void testGetReservationById() throws Exception {
        Reservation reservation = reservationService.getReservationById(3);
        assertEquals("Réunion",reservation.getTitre());
    }
}


