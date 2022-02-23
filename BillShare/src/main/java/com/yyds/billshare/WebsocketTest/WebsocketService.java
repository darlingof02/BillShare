package com.yyds.billshare.WebsocketTest;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.yyds.billshare.Model.Bill;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

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
    public void notifyUserBill(final String id, final Bill bill) throws JsonProcessingException {
        logger.info(bill.toString());
        logger.info(bill.getOwner().toString());
        bill.setOwner(null);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        logger.info(ow.writeValueAsString(bill));
        messagingTemplate.convertAndSendToUser(id, "/topic/private-greetings", ow.writeValueAsString(bill) );
    }
}
