package com.yyds.billshare.WebsocketTest;

import com.sun.security.auth.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.security.Principal;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

//    @Bean
//    public UserHandshakeHandler userHandshakeHandler(){
//        return new UserHandshakeHandler();
//    }

//    @Autowired
//    private UserHandshakeHandler userHandshakeHandler;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        registry.addEndpoint("/gs-guide-websocket")
                .setAllowedOrigins("http://localhost:3000/")
                .setHandshakeHandler(new UserHandshakeHandler())
                .withSockJS();
//        registry.addEndpoint("/gs-guide-websocket").setAllowedOrigins("http://localhost:3001/").withSockJS();

    }
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                logger.warn("fuck you");
                logger.warn(message.toString());
                StompHeaderAccessor accessor =
                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                logger.warn(accessor.toString());
                logger.warn(accessor.getSessionId());
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    logger.warn("this is connect");
                    Principal user = accessor.getUser(); ; // access authentication header(s)
                    if(user==null)
                        logger.warn("this is null user");
                    else
                        logger.warn(user.toString());
//                    accessor.setUser(user);
                    accessor.setUser(new UserPrincipal(accessor.getSessionId()));
                }
//                throw new RuntimeException("Not Valid");
                return message;
            }
        });
    }

}