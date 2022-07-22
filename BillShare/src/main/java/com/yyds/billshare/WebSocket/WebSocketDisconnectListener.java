package com.yyds.billshare.WebSocket;

import com.yyds.billshare.OnlineDetect.OnlineDetectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;


public class WebSocketDisconnectListener implements ApplicationListener<SessionDisconnectEvent> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OnlineDetectService onlineDetectService;

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event){
        logger.warn("Listen disconnect");
        logger.warn(event.toString());
        Message<?> message = event.getMessage();
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message,StompHeaderAccessor.class);
        logger.warn("disconnect",accessor.getUser().toString());
        onlineDetectService.deleteUser(accessor.getUser().getName());
    }
}
