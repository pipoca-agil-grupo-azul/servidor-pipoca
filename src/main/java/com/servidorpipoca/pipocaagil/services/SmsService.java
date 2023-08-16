package com.servidorpipoca.pipocaagil.services;

import org.springframework.stereotype.Service;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class SmsService {

    private final String twilioSid = "ACe5c93ee42d3fdee561bd06e23ed12af9";

    private final String authToken = "5081b775dece32bb3c79c96e26a4ec19";

    private String phoneFrom = "+12054902658";

    private String phoneTo = "+5571996622763";

    public void sendSMS() {
        Twilio.init(twilioSid, authToken);

        Message message = Message
                .creator(new PhoneNumber(phoneTo), new PhoneNumber(phoneFrom), "Testando envio de SMS com java")
                .create();

        System.out.println(message.getSid());
    }
}
