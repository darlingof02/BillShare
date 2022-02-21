package com.yyds.billshare.WebsocketTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSocketController2 {
    @Autowired
    private WebsocketService websocketService;

    @PostMapping("/send-message")
    public void sendMessage(@RequestBody final HelloMessage message){
        websocketService.notifyFrontend(message.getName());
    }
    @PostMapping("/send-message/{id}")
    public void sendPrivateMessage(@PathVariable String id, @RequestBody final HelloMessage message){
        websocketService.notifyUser(id,message.getName());
    }
}
