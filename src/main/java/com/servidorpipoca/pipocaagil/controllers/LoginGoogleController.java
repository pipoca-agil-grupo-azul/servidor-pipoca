package com.servidorpipoca.pipocaagil.controllers;

import com.servidorpipoca.pipocaagil.models.dto.UserLoginGoogleDTO;
import com.servidorpipoca.pipocaagil.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin(origins = "${CROSS_ORIGIN}", allowedHeaders = "*")
@RestController
@RequestMapping("/logingoogle")
public class LoginGoogleController {

    @Autowired
    UserRepository userRepository;

    @GetMapping()
    public ResponseEntity<UserLoginGoogleDTO> loginGoogle(@AuthenticationPrincipal OidcUser userPrincipal) {
        String subject = userPrincipal.getSubject();
        String email = userPrincipal.getEmail();

        userRepository.findByEmail(email);

        UserLoginGoogleDTO userDTO = new UserLoginGoogleDTO(subject, email);

        return ResponseEntity.ok(userDTO);
    }
}
