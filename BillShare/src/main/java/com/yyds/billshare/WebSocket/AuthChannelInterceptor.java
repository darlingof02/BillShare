package com.yyds.billshare.WebSocket;

import com.sun.security.auth.UserPrincipal;
import com.yyds.billshare.Controller.ControllerHelper;
import com.yyds.billshare.OnlineDetect.OnlineDetectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;


public class AuthChannelInterceptor implements ChannelInterceptor {
    @Autowired
    private ControllerHelper controllerHelper;
    @Autowired
    private OnlineDetectService onlineDetectService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
//        logger.info("inboundChannel message:\n" + message.toString());
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message,StompHeaderAccessor.class);

        if(StompCommand.CONNECT.equals(accessor.getCommand())){
            String token = WebSocketUtil.getJwtFromMessage(message);
            if(token == null)
                throw new RuntimeException();
            String userEmail = controllerHelper.getEmailFromJWT(token);
            logger.info(accessor.toString());
            accessor.setUser(new UserPrincipal(userEmail));
//             set user online
            onlineDetectService.setUserOnline(userEmail, accessor.getSessionId());
            logger.info("Connect user:"+userEmail);
        }

        return message;
    }

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        ChannelInterceptor.super.postSend(message, channel, sent);
    }
}
