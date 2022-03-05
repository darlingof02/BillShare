package com.yyds.billshare.Controller;

import com.yyds.billshare.Model.Bill;
import com.yyds.billshare.Model.InDebt;
import com.yyds.billshare.Model.Form.BillCreateForm;
import com.yyds.billshare.Model.Form.DebtorInfo;
import com.yyds.billshare.Model.ResponseModel.ResponseDebtForOneBill;
import com.yyds.billshare.Model.ResponseModel.ResponseOneBill;
import com.yyds.billshare.Model.ResponseModel.ResponseOwnedBill;
import com.yyds.billshare.Model.User;
import com.yyds.billshare.Repository.BillRepository;
import com.yyds.billshare.Repository.InDebtRepository;
import com.yyds.billshare.Repository.UserRepository;
import com.yyds.billshare.jwt.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
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

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${path.receipt}")
    private String receiptSavePath;

    @Autowired
    private BillRepository billRepository;
    @Autowired
    private InDebtRepository inDebtRepository;
    @Autowired
    private ControllerHelper controllerHelper;

    @PostMapping("/createbill")
    public String createBill(@Valid BillCreateForm billCreateForm,
                             BindingResult bindingResult,
                             @RequestHeader(value = "Authorization") String token) throws IOException {
        if(bindingResult.hasErrors()){
            return bindingResult.getAllErrors().toString();
        }

        Bill bill = new Bill(billCreateForm);
        User owner = controllerHelper.getUserFromJWT(token.substring(7));
        bill.setOwner(owner);
        if(billCreateForm.getReceipt()!=null && !billCreateForm.getReceipt().isEmpty())
            this.saveReceipt(billCreateForm.getReceipt());

        billRepository.save(bill);
        //save debtors
        for(DebtorInfo debtorInfo: billCreateForm.getDebtorInfos()){
            User d = controllerHelper.getUserByEmail(debtorInfo.getDebtorEmail());
            InDebt inDebt = new InDebt(d,bill,0,null,null,debtorInfo.getAmount());
            inDebtRepository.save(inDebt);
        }
        return "create bill successfully";
    }
    @PostMapping("/create-bill")
    public ResponseEntity<?> createBillJson(@Valid @RequestBody BillCreateForm billCreateForm,
                                         BindingResult bindingResult,
                                         @RequestHeader(value = "Authorization", required = false) String token) throws IOException {
        if(bindingResult.hasErrors()){
            return new ResponseEntity<String>(bindingResult.getAllErrors().toString(), HttpStatus.BAD_REQUEST);
        }

        logger.info(billCreateForm.toString());

        Bill bill = new Bill(billCreateForm);
        User owner = controllerHelper.getUserFromJWT(token.substring(7));
        bill.setOwner(owner);
        if(billCreateForm.getReceipt()!=null && !billCreateForm.getReceipt().isEmpty())
            this.saveReceipt(billCreateForm.getReceipt());

        billRepository.save(bill);
        //save debtors
        for(DebtorInfo debtorInfo: billCreateForm.getDebtorInfos()){
            User d = controllerHelper.getUserByEmail(debtorInfo.getDebtorEmail());
            logger.warn(debtorInfo.getAmount().toString());
            InDebt inDebt = new InDebt(d,bill,0,null,null,debtorInfo.getAmount());
            inDebtRepository.save(inDebt);
        }
        return new ResponseEntity<Void>(HttpStatus.OK);
    }


    /**
     * Used to require bills created by user
     * @param token (JWT Token)
     * @return List of ResponseOwnedBill
     */
    @GetMapping("/owned_bills")
    public List<ResponseOwnedBill> getOwnedBills(@RequestHeader(value = "Authorization") String token){
//        System.out.println("what the hell");
        User owner = controllerHelper.getUserFromJWT(token.substring(7));
        List<ResponseOwnedBill> bills =  billRepository.findByOwnerId(owner.getUid());

        logger.warn(bills.get(0).toString());
        return bills;
    }
//    @GetMapping("/test/owned_bills")
//    public List<ResponseOwnedBill> getOwned(@RequestHeader(value = "Authorization") String token){
////        System.out.println("what the hell");
//        User owner = controllerHelper.getUserFromJWT(token.substring(7));
//        List<Bill> bills =  billRepository.findByAmount(10000);
//        logger.warn(bills.toString());
//
//        logger.warn(bills.get(0).toString());
//        return null;
//    }

    /**
     * used to get All InDebtor and their Amount for a certain Bill
     * @param bid (id of certain bill that user created)
     * @param token (JWT Token of certain user)
     * @return A list of ResponseDebt
     */
    @GetMapping("/owned_bills/{bid}")
    public List<ResponseDebtForOneBill> getDebtorsByBill(@PathVariable Integer bid, @RequestHeader(value = "Authorization") String token){
        User owner = controllerHelper.getUserFromJWT(token.substring(7));
        Bill bill = billRepository.getById(bid);
        if(!bill.getOwner().equals(owner)){
            log.warn("Unable to fetch data. " + owner.getEmail() + " has no permission!");
            throw new RuntimeException();
        }

        List<ResponseDebtForOneBill> indebts = inDebtRepository.findByBill(bill);
        logger.warn(indebts.get(0).toString());
        return indebts;
    }

    @GetMapping("/history_in_debt_bills")
    public List<InDebt> getAllInDebtBills(@RequestHeader(value = "Authorization") String token){
        User debtor = controllerHelper.getUserFromJWT(token.substring(7));
        return inDebtRepository.findByDebtor(debtor);
    }

    @GetMapping("/bills_to_be_paid")
    public List<InDebt> getBillsToBePaid(@RequestHeader(value = "Authorization") String token){
        User debtor = controllerHelper.getUserFromJWT(token.substring(7));
        return inDebtRepository.findByDebtorAndStatus(debtor,0);
    }

    @GetMapping("/bills/{bid}")
    public ResponseOneBill getOneBill(@PathVariable Integer bid, @RequestHeader(value = "Authorization") String token) {
        List<ResponseOneBill> bills = billRepository.findByBid(bid);
        logger.warn(bills.get(0).toString());
        return bills.get(0);
    }

//    =========================确认欠款==============================
    public void accept_bill(){}

//    =========================付款==============================
    public void pay(){}

//    =========================确认付款==============================
    public void confirm_pay(){}




    private void saveReceipt(MultipartFile receipt) throws IOException {
        String avatarPath = receipt.getOriginalFilename();
        String saveFilePath = receiptSavePath + File.separator + avatarPath;
        receipt.transferTo(new File(saveFilePath));
    }

}
