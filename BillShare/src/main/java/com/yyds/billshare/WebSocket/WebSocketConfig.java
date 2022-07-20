package com.yyds.billshare.WebSocket;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    final private int HEART_BEAT = 10_000;

    @Bean
    public AuthChannelInterceptor authChannelInterceptor(){
        return new AuthChannelInterceptor();
    }
    @Bean
    public MessageChannelInterceptor messageChannelInterceptor(){
        return new MessageChannelInterceptor();
    }
    @Bean
    public WebSocketDisconnectListener webSocketConnectListener(){
        return new WebSocketDisconnectListener();
    }
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/gs-guide-websocket")
                .setAllowedOrigins("http://localhost:3000/","http://localhost:3001/","http://localhost:3002/")
                .withSockJS();
    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {


        ThreadPoolTaskScheduler te = new ThreadPoolTaskScheduler();
        te.setPoolSize(1);
        te.setThreadNamePrefix("wss-heartbeat-thread-");
        te.initialize();

        registry.enableSimpleBroker("/topic")
                .setHeartbeatValue(new long[]{HEART_BEAT, HEART_BEAT})
                .setTaskScheduler(te);
        registry.setApplicationDestinationPrefixes("/app");


    }
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(authChannelInterceptor());
        registration.interceptors(messageChannelInterceptor());
    }



}
