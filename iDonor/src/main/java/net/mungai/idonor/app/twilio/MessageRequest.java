package net.mungai.idonor.app.twilio;

import org.springframework.context.annotation.Bean;

public class MessageRequest {

    private String message;

    public MessageRequest(String message){
        this.message =message;
    }

    @Bean
    public String getMessage() {
        return message;
    }

    @Bean
    public void setMessage(String message) {
        this.message = message;
    }
}
