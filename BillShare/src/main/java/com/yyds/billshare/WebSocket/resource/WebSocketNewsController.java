package com.yyds.billshare.WebSocket.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yyds.billshare.Model.Bill;
import com.yyds.billshare.Repository.BillRepository;
import com.yyds.billshare.WebsocketTest.WebsocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WebSocketNewsController {
    @Autowired
    private WebsocketService websocketService;
    @Autowired
    private BillRepository billRepository;

    private Logger logger = LoggerFactory.getLogger(this.getClass());


//
//    @PostMapping("/send-bill-message/{id}")
//    public void sendPrivateBillMessage(@PathVariable String id) throws JsonProcessingException {
//
//        List<Bill> allbills = billRepository.findAll();
//        logger.warn("print bills *");
//        logger.warn(allbills.toString());
//        websocketService.notifyUserBill(id,allbills.get(0));
//    }


}
