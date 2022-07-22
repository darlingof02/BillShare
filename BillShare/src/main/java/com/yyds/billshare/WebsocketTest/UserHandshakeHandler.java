package com.yyds.billshare.WebsocketTest;

import com.sun.security.auth.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class UserHandshakeHandler extends DefaultHandshakeHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        System.out.println(request.toString());
        String randomId = UUID.randomUUID().toString();
        logger.info("User with id '{}' opened the page", randomId);
//        logger.info("User with id '{}' opened the page");
        return new UserPrincipal(randomId);
    }
}
