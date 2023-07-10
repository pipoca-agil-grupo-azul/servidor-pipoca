package com.servidorpipoca.pipocaagil.services;

import com.servidorpipoca.pipocaagil.security.JwtTokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrations;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

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

    public String requestAccessToken(OidcUser principal, HttpServletResponse response) {
        //ClientRegistration clientRegistration = ClientRegistrations.fromIssuerLocation("http://localhost:8080/public").build();
        //String jwt = authorize(clientRegistration.getRegistrationId(), principal.getAttribute("email")).getAccessToken().getTokenValue();

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(principal.getAttribute("email"), principal.getAttribute("password"))

        );

        tokenProvider.addTokenToResponse(  , response);
    }
}
