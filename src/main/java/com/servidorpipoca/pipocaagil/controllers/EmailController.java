package com.servidorpipoca.pipocaagil.controllers;

import com.servidorpipoca.pipocaagil.models.dto.SendEmailDTO;
import com.servidorpipoca.pipocaagil.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "${CROSS_ORIGIN}", allowedHeaders = "*")
@RestController
@RequestMapping("/email")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping()
    public ResponseEntity<?> recoverPasswordEmail(@RequestBody SendEmailDTO email) {
        return emailService.checkIfEmailExists(email);
    }
}
