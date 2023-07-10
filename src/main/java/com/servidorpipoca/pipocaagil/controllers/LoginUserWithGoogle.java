package com.servidorpipoca.pipocaagil.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.servidorpipoca.pipocaagil.security.JwtTokenProvider;
import com.servidorpipoca.pipocaagil.services.OAuth2TokenRequester;

//@CrossOrigin(origins = "https://localhost:8080/", allowedHeaders = "*")
@RestController
//@RequestMapping("/login/google")
public class LoginUserWithGoogle {

    @Autowired
    private OAuth2TokenRequester tokenRequester;

    @GetMapping("/public")
    public String publicRoute() {
        return "Rota pública";
    }

    @GetMapping("/private")
    public String privateRoute(@AuthenticationPrincipal OidcUser userPrincipal, HttpServletResponse response) {
        return String.format("""
                    <h1> ---- Rota privada! ---- </h1>

                    <h2> Principal: %s </h2>
                    <h2> Email: %s </h2>
                    <h3> Authorities: %s </h3>
                    <h4> JWT: %s </h4>
                """, userPrincipal, userPrincipal.getAttribute("email"), userPrincipal.getAuthorities(), userPrincipal.getIdToken().getTokenValue());
//
//        String token = tokenRequester.requestAccessToken(userPrincipal);
//        return ResponseEntity.ok().body(token);
    }
}
