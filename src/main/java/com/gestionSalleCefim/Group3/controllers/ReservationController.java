package com.gestionSalleCefim.Group3.controllers;

import com.gestionSalleCefim.Group3.entities.Reservation;
import com.gestionSalleCefim.Group3.repositories.ReservationRepository;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/reservation")
public class ReservationController extends BaseController<Reservation, ReservationRepository>{ }
