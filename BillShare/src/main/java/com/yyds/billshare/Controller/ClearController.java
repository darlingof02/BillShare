package com.yyds.billshare.Controller;


import com.yyds.billshare.Model.Bill;
import com.yyds.billshare.Model.Form.BillCreateForm;
import com.yyds.billshare.Model.Form.DebtorInfo;
import com.yyds.billshare.Model.InDebt;
import com.yyds.billshare.Model.User;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@RestController
@CrossOrigin(origins = {"http://localhost:3000/","http://localhost:3001/","http://localhost:3002/"}, allowCredentials = "true", allowedHeaders = "*")
public class ClearController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/createbill")
    public ResponseEntity<?> createBillJson(@RequestHeader(value = "Authorization", required = false) String token) throws IOException {





        return null;
    }

}
