package com.servidorpipoca.pipocaagil.controllers;

import com.servidorpipoca.pipocaagil.models.dto.UserDTO;
import com.servidorpipoca.pipocaagil.repositories.UserRepository;
import com.servidorpipoca.pipocaagil.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "${CROSS_ORIGIN}", allowedHeaders = "*")
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    public AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<String> login(@RequestBody UserDTO dto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.username(), dto.password())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.ok("Login realizado com sucesso!");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha na autenticação: " + e.getMessage());
        }
    }
}
