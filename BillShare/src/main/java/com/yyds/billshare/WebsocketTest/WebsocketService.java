package com.yyds.billshare.WebsocketTest;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WebsocketService {
    private final SimpMessagingTemplate messagingTemplate;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    public WebsocketService(SimpMessagingTemplate simpMessagingTemplate) {
        this.messagingTemplate = simpMessagingTemplate;
    }
    public void notifyFrontend(final String message){
        logger.info(message);
        messagingTemplate.convertAndSend("/topic/greetings", new Greeting(message));
    }
    public void notifyUser(final String id, final String message){
        logger.info(message);
        messagingTemplate.convertAndSendToUser(id, "/topic/private-greetings", new Greeting(message));
    }


}
