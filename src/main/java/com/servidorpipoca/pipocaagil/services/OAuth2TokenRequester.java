package com.servidorpipoca.pipocaagil.services;

import com.nimbusds.openid.connect.sdk.AuthenticationErrorResponse;
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
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrations;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OAuth2TokenRequester {

    @Autowired
    private OAuth2AuthorizedClientManager authorizedClientManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;


    public OAuth2AuthorizedClient authorize(String clientRegistrationID, String principal) {
        OAuth2AuthorizeRequest request = OAuth2AuthorizeRequest.withClientRegistrationId(clientRegistrationID).principal(principal).build();
        return authorizedClientManager.authorize(request);
    }

    public ResponseEntity<Map<String, Object>> requestAccessToken(OidcUser principal, HttpServletResponse response) {
        //ClientRegistration clientRegistration = ClientRegistrations.fromIssuerLocation("http://localhost:8080/public").build();
        //String jwt = authorize(clientRegistration.getRegistrationId(), principal.getAttribute("email")).getAccessToken().getTokenValue();

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(principal.getAttribute("email"), principal.getAttribute("password"))

            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("token", principal.getIdToken().getTokenValue());

            return ResponseEntity.ok(responseBody);

        } catch (AuthenticationException e) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("message", "Falha na autenticação: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
        }
    }
}
