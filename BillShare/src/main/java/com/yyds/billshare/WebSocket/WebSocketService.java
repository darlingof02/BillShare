package com.yyds.billshare.WebSocket;


import com.yyds.billshare.WebsocketTest.WebsocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {
    private final SimpMessagingTemplate messagingTemplate;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public WebSocketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendDebtToUser(final String user ,final String debtInfo){
        messagingTemplate.convertAndSendToUser(user,"/topics/debts",debtInfo);
    }




}
