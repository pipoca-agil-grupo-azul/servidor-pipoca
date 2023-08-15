package com.servidorpipoca.pipocaagil.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail() {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo("mateuscristofori@gmail.com");
        message.setSubject("Testando envio de email");
        message.setText("Testando envio de Email com o Javinha");
        javaMailSender.send(message);
    }
}