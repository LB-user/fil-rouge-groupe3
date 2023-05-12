package com.gestionSalleCefim.Group3.controllers;

import com.gestionSalleCefim.Group3.entities.Building;
import com.gestionSalleCefim.Group3.repositories.BuildingRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/building")
public class BuildingController extends BaseController<Building, BuildingRepository>{
}
