package com.servidorpipoca.pipocaagil.services;

import org.springframework.stereotype.Service;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class SmsService {

    private final String twilioSid = "ACe5c93ee42d3fdee561bd06e23ed12af9";

    private final String authToken = "bc7d3a83b58a49bc2777e4d63cc3bb6f";

    private String phoneFrom = "+12054902658";

    public void sendSMS(String phoneTo) {
        Twilio.init(twilioSid, authToken);

        Message message = Message
                .creator(new PhoneNumber(phoneTo), new PhoneNumber(phoneFrom), "Testando envio de SMS com java")
                .create();

        System.out.println(message.getSid());
    }
}