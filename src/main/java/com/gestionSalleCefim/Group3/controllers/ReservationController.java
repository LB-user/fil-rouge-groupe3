package com.gestionSalleCefim.Group3.controllers;

import com.gestionSalleCefim.Group3.entities.Reservation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@RestController
@Validated
@RequestMapping("/api/reservation")
public class ReservationController extends BaseController<Reservation>{ }
