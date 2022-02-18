package com.yyds.billshare.ChatTeset;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@ServerEndpoint(value = "/test/websocket")
public class MyWebsiteSocket {
    private static Map<String, MyWebsiteSocket> websiteSocketMap = new LinkedHashMap<>();
    private static int count = 0;
    private Session session;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        websiteSocketMap.put(this.session.getId(), this);
        MyWebsiteSocket.addCount();
        log.info("new connection joined. " + session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session){
        logger.info("receive message from client: "+message);
        try {
            session.getBasicRemote().sendText("we have received your message! " + message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(){
        websiteSocketMap.remove(this.session.getId());
        reduceCount();
        logger.info(session.getId() + " has closed!");
    }

    public static void broadcast(){
        websiteSocketMap.forEach((k, v)->{
            try {
                v.getSession().getBasicRemote().sendText("this is a broadcast!");
            } catch (Exception e){
                e.printStackTrace();
            }

        });
    }

    public Session getSession(){
        return session;
    }


    private static synchronized void addCount(){
        MyWebsiteSocket.count++;
    }
    private static synchronized void reduceCount(){
        MyWebsiteSocket.count--;
    }


}
