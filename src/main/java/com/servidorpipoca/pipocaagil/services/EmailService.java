package com.servidorpipoca.pipocaagil.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String to) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject("Recuperação de senha - Pipoca Ágil");
        message.setText("""

                Link para a troca de senha

                - http://localhost:5173/recoverPassword

                """);
        javaMailSender.send(message);
    }
}