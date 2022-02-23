package com.yyds.billshare.WebsocketTest.Model;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WSMessage {
    String from;
    String to;
    Object payload;
    String type;
}
