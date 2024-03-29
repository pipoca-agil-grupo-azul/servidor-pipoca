package com.servidorpipoca.pipocaagil.services;

import com.servidorpipoca.pipocaagil.models.User;
import com.servidorpipoca.pipocaagil.models.dto.UserLoginDTO;
import com.servidorpipoca.pipocaagil.repositories.UserRepository;
import com.servidorpipoca.pipocaagil.security.JwtTokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {
    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public AuthenticationManager authenticationManager;


    public ResponseEntity<Map<String, Object>> login(@Valid UserLoginDTO dto, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.email(), dto.password()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            tokenProvider.addTokenToResponse(authentication, response);

            User user = userRepository.findByEmail(dto.email());

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("token", tokenProvider.generateToken(authentication));
            responseBody.put("id", user.getId());
            responseBody.put("email", dto.email());
            responseBody.put("nome", user.getName());

            return ResponseEntity.ok(responseBody);
        } catch (AuthenticationException e) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Falha na autenticação: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
        }
    }
}
