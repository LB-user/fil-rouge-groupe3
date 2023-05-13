package com.gestionSalleCefim.Group3.controllers;

import com.gestionSalleCefim.Group3.entities.User;
import com.gestionSalleCefim.Group3.repositories.UserRepository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/api/user")
public class UserController extends BaseController<User, UserRepository>{
}