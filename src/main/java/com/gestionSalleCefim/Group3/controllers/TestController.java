package com.gestionSalleCefim.Group3.controllers;

import com.gestionSalleCefim.Group3.entities.User;
import com.gestionSalleCefim.Group3.services.EmailService;
import com.gestionSalleCefim.Group3.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/test")
public class TestController {
    @Autowired
    EmailService emailService;
    @Autowired
    UserService userService;

    @GetMapping("/get-code")
    @Operation(hidden = true)
    public String getCode() throws MessagingException {
        User user = userService.getById(1);
        emailService.sendRegisterMail(user, "sddgqsdf");
        return "Test ok";
    }
}
