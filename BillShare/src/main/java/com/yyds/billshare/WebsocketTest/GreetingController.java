package com.yyds.billshare.WebsocketTest;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;

@Slf4j
@Controller
@CrossOrigin(origins = {"http://localhost:3000/","http://localhost:3001/"}, allowCredentials = "true", allowedHeaders = "*")
public class GreetingController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    public SimpUserRegistry simpUserRegistry;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        logger.info(message.getName());
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

    @MessageMapping("/private-hello")
    @SendToUser("/topic/private-greetings")
    public Greeting privateGreeting(HelloMessage message, Principal principal) throws Exception {
        logger.warn(message.getName());
        if(principal!=null)
            logger.warn(principal.toString());
        for(SimpUser u : simpUserRegistry.getUsers()){
            logger.info("shithasdta");
            logger.warn(u.toString());
        }
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "! This is private greeting only for you!");
    }

}
