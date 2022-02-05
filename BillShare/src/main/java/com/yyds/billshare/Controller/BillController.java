package com.yyds.billshare.Controller;

import com.yyds.billshare.Model.Bill;
import com.yyds.billshare.Model.Form.BillCreateForm;
import com.yyds.billshare.Model.User;
import com.yyds.billshare.Repository.BillRepository;
import com.yyds.billshare.Repository.UserRepository;
import com.yyds.billshare.jwt.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:3000/", allowCredentials = "true", allowedHeaders = "*")
public class BillController {

    @Value("/Users/yuning/Documents/GitHub/BillShare/BillShare/src/main/resources/static/image/receipt")
    private String receiptSavePath;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserRepository userJpaRepository;
    @Autowired
    private BillRepository billRepository;


    @PostMapping("/createbill")
    public String createBill(@Valid BillCreateForm billCreateForm, @RequestHeader(value = "Authorization") String token) throws IOException {
        Bill bill = new Bill(billCreateForm);
        User owner = getUserFromJWT(token.substring(7));
        bill.setOwner(owner);
        if(!billCreateForm.getReceipt().isEmpty())
            this.saveReceipt(billCreateForm.getReceipt());
        log.info(bill.toString());
        billRepository.save(bill);
        return "create bill successfully";
    }

    private User getUserFromJWT(String token){
        String email = jwtTokenUtil.getUsernameFromToken(token);
        List<User> users = userJpaRepository.findByEmail(email);
        if(users==null || users.isEmpty())
            throw new UsernameNotFoundException(String.format("USER_NOT_FOUND '%s'.", email));
        return users.get(0);
    }
    private void saveReceipt(MultipartFile receipt) throws IOException {
        String avatarPath = receipt.getOriginalFilename();
        String saveFilePath = receiptSavePath + File.separator + avatarPath;
        receipt.transferTo(new File(saveFilePath));
    }
}
