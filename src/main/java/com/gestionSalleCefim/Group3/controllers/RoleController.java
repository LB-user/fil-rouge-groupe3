package com.gestionSalleCefim.Group3.controllers;

import com.gestionSalleCefim.Group3.entities.Role;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/role")
public class RoleController extends BaseController<Role>{
}