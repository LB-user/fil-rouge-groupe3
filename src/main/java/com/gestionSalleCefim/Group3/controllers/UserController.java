package com.gestionSalleCefim.Group3.controllers;

import com.gestionSalleCefim.Group3.entities.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController extends BaseController<User>{
}
