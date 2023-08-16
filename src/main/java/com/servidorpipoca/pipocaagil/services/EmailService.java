package com.servidorpipoca.pipocaagil.services;

import com.servidorpipoca.pipocaagil.models.dto.SendEmailDTO;
import com.servidorpipoca.pipocaagil.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserRepository userRepository;

    public void sendEmail(String to) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject("Recuperação de senha - Pipoca Ágil");
        message.setText("Recuperação de senha");
        javaMailSender.send(message);
    }

    public ResponseEntity<?> checkIfEmailExists(SendEmailDTO email) {
        if (userRepository.existsByEmail(email.email())) {
            sendEmail(email.email());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
