package com.yyds.billshare.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000/", allowCredentials = "true", allowedHeaders = "*")
public class BillController {
    @PostMapping("/createbill")
    public String createBill() {

        return "shit";
    }


}
