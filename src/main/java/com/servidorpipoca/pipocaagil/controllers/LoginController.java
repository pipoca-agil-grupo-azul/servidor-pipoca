package com.servidorpipoca.pipocaagil.controllers;

import com.servidorpipoca.pipocaagil.models.dto.UserLoginDTO;
import com.servidorpipoca.pipocaagil.security.JwtTokenProvider;
import jakarta.servlet.http.HttpServletResponse;
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
    private JwtTokenProvider tokenProvider;

    @Autowired
    public AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<String> login(@RequestBody UserLoginDTO dto, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.email(), dto.password()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            tokenProvider.addTokenToResponse(authentication, response);
            return ResponseEntity.ok("Login realizado com sucesso!");

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha na autenticação: " + e.getMessage());
        }
    }
}
