package com.servidorpipoca.pipocaagil.controllers;

import com.servidorpipoca.pipocaagil.models.dto.UserLoginDTO;
import com.servidorpipoca.pipocaagil.services.LoginService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "${CROSS_ORIGIN}", allowedHeaders = "*")
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserLoginDTO dto, HttpServletResponse response) {
        return loginService.login(dto, response);
    }

}
