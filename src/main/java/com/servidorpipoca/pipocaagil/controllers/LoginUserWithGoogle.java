package com.servidorpipoca.pipocaagil.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.Response;
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
    public ResponseEntity privateRoute(@AuthenticationPrincipal OidcUser userPrincipal, HttpServletResponse response) {
        return tokenRequester.requestAccessToken(userPrincipal, response);
    }
}
