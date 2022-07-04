package net.mungai.idonor.app.twilio;

import org.springframework.context.annotation.Configuration;

import com.twilio.Twilio;

@Configuration
public class Twilioinitializer {

    private final Twilioproperties twilioProperties;

    public Twilioinitializer(Twilioproperties twilioproperties)
    {
        this.twilioProperties =twilioproperties;
        Twilio.init(twilioproperties.getAccountSid(), twilioproperties.getAuthToken());
        System.out.println("twilio initialized with account: "+twilioproperties.getAccountSid());
    }

}
