package com.yyds.billshare.ChatTeset;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class TestWebSocketController {

    @GetMapping("/test/broadcast")
    public void broadcast(){
        MyWebsiteSocket.broadcast();
    }
}
