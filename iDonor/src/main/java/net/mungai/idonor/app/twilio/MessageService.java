package net.mungai.idonor.app.twilio;

import org.springframework.stereotype.Service;

@Service
public class MessageService {

    public String getMessage(MessageRequest messageRequest){;
        return messageRequest.getMessage();
    }
}
