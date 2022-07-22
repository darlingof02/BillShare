package com.yyds.billshare.WebSocket;

import org.springframework.messaging.Message;
import org.springframework.util.LinkedMultiValueMap;

import java.util.List;

public class WebSocketUtil {
    static public String getJwtFromMessage (Message<?> message){
        LinkedMultiValueMap<String,String> nativeHeaders = message.getHeaders().get("nativeHeaders", LinkedMultiValueMap.class);
        if(nativeHeaders == null)
            return null;
        List<String> authorizations = nativeHeaders.get("Authorization");
        if(authorizations ==null || authorizations.isEmpty())
            return null;
        return authorizations.get(0);
    }
}
