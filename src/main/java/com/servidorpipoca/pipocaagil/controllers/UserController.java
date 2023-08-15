package com.servidorpipoca.pipocaagil.controllers;

import com.servidorpipoca.pipocaagil.models.User;
import com.servidorpipoca.pipocaagil.models.dto.SendEmailDTO;
import com.servidorpipoca.pipocaagil.models.dto.UserCreateDTO;
import com.servidorpipoca.pipocaagil.models.dto.UserUpdateDTO;
import com.servidorpipoca.pipocaagil.services.EmailService;
import com.servidorpipoca.pipocaagil.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "${CROSS_ORIGIN}", allowedHeaders = "*")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        User obj = userService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping("/{email}")
    public ResponseEntity<?> existsByEmail(@RequestBody String email) {
        return userService.existsUserByEmail(email);
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody UserCreateDTO user) {
        User createdUser = userService.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody UserUpdateDTO user) {
        User updatedUser = userService.update(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/email")
    public void email(@RequestBody SendEmailDTO sendEmail) {
        emailService.sendEmail(sendEmail.to(), sendEmail.subject(), sendEmail.content());
    }
}
