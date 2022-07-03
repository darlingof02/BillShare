package com.yyds.billshare.WebSocket;

import com.sun.security.auth.UserPrincipal;
import com.yyds.billshare.Controller.ControllerHelper;
import com.yyds.billshare.jwt.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.util.LinkedMultiValueMap;

import java.util.List;

public class AuthChannelInterceptor implements ChannelInterceptor {
    @Autowired
    private ControllerHelper controllerHelper;


    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    public String getJwtFromMessage (Message<?> message){
        LinkedMultiValueMap<String,String> nativeHeaders = message.getHeaders().get("nativeHeaders", LinkedMultiValueMap.class);
        if(nativeHeaders == null)
            return null;
        List<String> authorizations = nativeHeaders.get("Authorization");
        if(authorizations ==null || authorizations.isEmpty())
            return null;
        return authorizations.get(0);
    }
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        logger.info("inboundChannel message:\n" + message.toString());
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message,StompHeaderAccessor.class);

//        logger.warn(message.getHeaders().get("nativeHeaders", LinkedMultiValueMap.class).get("login").get(0).toString());
//        logger.warn(message.getHeaders().get("nativeHeaders", LinkedMultiValueMap.class).get("passcode").get(0).getClass().toString());

        if(StompCommand.CONNECT.equals(accessor.getCommand())){
            String token = getJwtFromMessage(message);
            if(token == null)
                throw new RuntimeException();
            String userEmail = controllerHelper.getEmailFromJWT(token);
            accessor.setUser(new UserPrincipal(userEmail));
            logger.info("Connect user:"+userEmail);
        }
        return message;
    }

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        ChannelInterceptor.super.postSend(message, channel, sent);
    }
}
