package com.servidorpipoca.pipocaagil.services;

import com.servidorpipoca.pipocaagil.models.dto.SendEmailDTO;
import com.servidorpipoca.pipocaagil.repositories.UserRepository;
import com.servidorpipoca.pipocaagil.security.JwtTokenProvider;
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

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public void sendEmail(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        String token = jwtTokenProvider.generatePasswordResetToken(to);

        message.setTo(to);
        message.setSubject("Recuperação de senha - Pipoca Ágil");
        message.setText("Para redefinir sua senha, clique neste link: https://site-pipoca.vercel.app/reset-password?token=" + token);
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
