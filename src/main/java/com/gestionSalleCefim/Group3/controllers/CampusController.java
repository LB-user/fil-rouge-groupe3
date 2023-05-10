package com.gestionSalleCefim.Group3.controllers;

import com.gestionSalleCefim.Group3.entities.Campus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/campus")
public class CampusController extends BaseController<Campus>{ }
