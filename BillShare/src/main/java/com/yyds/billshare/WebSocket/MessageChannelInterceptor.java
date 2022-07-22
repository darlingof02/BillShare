package com.yyds.billshare.WebSocket;

import com.sun.security.auth.UserPrincipal;
import com.yyds.billshare.Controller.ControllerHelper;
import com.yyds.billshare.OnlineDetect.OnlineDetectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;

public class MessageChannelInterceptor  implements ChannelInterceptor {
    @Autowired
    private OnlineDetectService onlineDetectService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message,StompHeaderAccessor.class);
        StompCommand command = accessor.getCommand();
//        logger.info("message interceptor:\n" + message);
        if(StompCommand.CONNECT.equals(command) || StompCommand.DISCONNECT.equals(command))
            return message;
        if(accessor.getUser()!=null){
            onlineDetectService.setUserOnline(accessor.getUser().getName(), accessor.getSessionId());
            logger.info("renew user:", accessor.getUser().getName());
        }

        return message;
    }

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        ChannelInterceptor.super.postSend(message, channel, sent);
    }
}
