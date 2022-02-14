package com.yyds.billshare.Controller;

import com.yyds.billshare.Model.Bill;
import com.yyds.billshare.Model.InDebt;
import com.yyds.billshare.Model.Form.BillCreateForm;
import com.yyds.billshare.Model.Form.DebtorInfo;
import com.yyds.billshare.Model.User;
import com.yyds.billshare.Repository.BillRepository;
import com.yyds.billshare.Repository.InDebtRepository;
import com.yyds.billshare.Repository.UserRepository;
import com.yyds.billshare.jwt.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
    @Autowired
    private InDebtRepository inDebtRepository;

    @PostMapping("/createbill")
    public String createBill(@Valid BillCreateForm billCreateForm,
                             BindingResult bindingResult,
                             @RequestHeader(value = "Authorization") String token) throws IOException {
        if(bindingResult.hasErrors()){
            return bindingResult.getAllErrors().toString();
        }

        Bill bill = new Bill(billCreateForm);
        User owner = getUserFromJWT(token.substring(7));
        bill.setOwner(owner);
        if(!billCreateForm.getReceipt().isEmpty())
            this.saveReceipt(billCreateForm.getReceipt());

        billRepository.save(bill);
        //save debtors
        for(DebtorInfo debtorInfo: billCreateForm.getDebtorInfos()){
            User d = getUserByEmail(debtorInfo.getDebtorEmail());
            InDebt inDebt = new InDebt(d,bill,0,null,null,debtorInfo.getAmount());
            inDebtRepository.save(inDebt);
        }
        return "create bill successfully";
    }

    @GetMapping("/owned_bills")
    public List<Bill> getOwnedBills(@RequestHeader(value = "Authorization") String token){
        User owner = getUserFromJWT(token.substring(7));
        return billRepository.findByOwner(owner);
    }

    @GetMapping("/owned_bills/{bid}")
    public List<InDebt> getDebtorsByBill(@PathVariable Integer bid, @RequestHeader(value = "Authorization") String token){
        User owner = getUserFromJWT(token.substring(7));
        Bill bill = billRepository.getById(bid);
        if(!bill.getOwner().equals(owner)){
            log.warn("Unable to fetch data. " + owner.getEmail() + " has no permission!");
            throw new RuntimeException();
        }
        return inDebtRepository.findByBill(bill);
    }

    @GetMapping("/history_in_debt_bills")
    public List<InDebt> getAllInDebtBills(@RequestHeader(value = "Authorization") String token){
        User debtor = getUserFromJWT(token.substring(7));
        return inDebtRepository.findByDebtor(debtor);
    }

    @GetMapping("/bills_to_be_paid")
    public List<InDebt> getBillsToBePaid(@RequestHeader(value = "Authorization") String token){
        User debtor = getUserFromJWT(token.substring(7));
        return inDebtRepository.findByDebtorAndStatus(debtor,0);
    }


    private User getUserByEmail(String email){
        List<User> users = userJpaRepository.findByEmail(email);
        if(users==null || users.isEmpty())
            throw new UsernameNotFoundException(String.format("USER_NOT_FOUND '%s'.", email));
        return users.get(0);
    }
    private User getUserFromJWT(String token){
        String email = jwtTokenUtil.getUsernameFromToken(token);
        return getUserByEmail(email);
    }
    private void saveReceipt(MultipartFile receipt) throws IOException {
        String avatarPath = receipt.getOriginalFilename();
        String saveFilePath = receiptSavePath + File.separator + avatarPath;
        receipt.transferTo(new File(saveFilePath));
    }

}
