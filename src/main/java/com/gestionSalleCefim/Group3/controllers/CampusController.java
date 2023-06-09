package com.gestionSalleCefim.Group3.controllers;

import com.gestionSalleCefim.Group3.entities.Campus;
import com.gestionSalleCefim.Group3.repositories.CampusRepository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api/campus")
public class CampusController extends BaseController<Campus, CampusRepository>{ }
