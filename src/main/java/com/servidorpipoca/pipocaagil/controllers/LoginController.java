package com.servidorpipoca.pipocaagil.controllers;

import com.servidorpipoca.pipocaagil.models.dto.UserDTO;
import com.servidorpipoca.pipocaagil.repositories.UserRepository;
import com.servidorpipoca.pipocaagil.security.JwtTokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "${CROSS_ORIGIN}", allowedHeaders = "*")
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    public AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @PostMapping
    public ResponseEntity<String> login(@RequestBody UserDTO dto, HttpServletResponse response) {

        return ResponseEntity.ok("Login realizado com sucesso!");
    }
}
