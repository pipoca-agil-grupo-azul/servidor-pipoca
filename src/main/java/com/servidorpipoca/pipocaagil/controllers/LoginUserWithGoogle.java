package com.servidorpipoca.pipocaagil.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin(origins = "https://localhost:8080/", allowedHeaders = "*")
@RestController
//@RequestMapping("/login/google")
public class LoginUserWithGoogle {

    @GetMapping("/public")
    public String publicRoute() {
        return "Rota pública";
    }

    @GetMapping("/private")
    public String privateRoute() {
        return "Rota privada";
    }
}
