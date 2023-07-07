package com.servidorpipoca.pipocaagil.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
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
    public String privateRoute(@AuthenticationPrincipal OidcUser userPrincipal) {
        return String.format("""
                    <h1> ---- Rota privada! ---- </h1>
                    
                    <h2> Principal: %s </h2>
                    <h2> Email: %s </h2>
                    <h3> Authorities: %s </h3>
                    <h4> JWT: %s </h4>
                """, userPrincipal, userPrincipal.getAttribute("email"), userPrincipal.getAuthorities(), userPrincipal.getIdToken().getTokenValue());
    }
}
