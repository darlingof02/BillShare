package com.yyds.billshare.Controller;

import com.yyds.billshare.Model.Bill;
import com.yyds.billshare.Model.InDebt;
import com.yyds.billshare.Model.Form.BillCreateForm;
import com.yyds.billshare.Model.Form.DebtorInfo;
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

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

// TODO: 所有的返回类型可以都改成ResponseEntity<原返回类型>，这样可以自定义response status或者header

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
    public ResponseEntity<String> createBill(@Valid BillCreateForm billCreateForm,
                             BindingResult bindingResult,
                             @RequestHeader(value = "Authorization") String token) throws IOException {
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getAllErrors().toString(), HttpStatus.BAD_REQUEST);
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
        return new ResponseEntity<>("create bill successfully", HttpStatus.CREATED);
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
        return new ResponseEntity<String>("Create bill successfully!",HttpStatus.CREATED);
    }


    @GetMapping("/owned_bills")
    public List<ResponseOwnedBill> getOwnedBills(@RequestHeader(value = "Authorization") String token){
        User owner = controllerHelper.getUserFromJWT(token.substring(7));
        List<ResponseOwnedBill> bills =  billRepository.findByOwnerId(owner.getUid());

//        logger.warn(bills.get(0).toString());
        return bills;
    }


    @GetMapping("/owned_bills/{bid}")
    public List<InDebt> getDebtorsByBill(@PathVariable Integer bid, @RequestHeader(value = "Authorization") String token){
        User owner = controllerHelper.getUserFromJWT(token.substring(7));
        Bill bill = billRepository.getById(bid);
        if(!bill.getOwner().equals(owner)){
            log.warn("Unable to fetch data. " + owner.getEmail() + " has no permission!");
            throw new RuntimeException();
        }
        return inDebtRepository.findByBill(bill);
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

//    =========================确认欠款==============================

    @PutMapping("/debt/{bid}")
    public ResponseEntity<?> UpgradeDebtStatus( @PathVariable Integer bid,
                                                @RequestHeader(value = "Authorization") String token) {
        String debtorEmail = controllerHelper.getEmailFromJWT(token);
        Optional<InDebt> debt = inDebtRepository.findByDebtorEmailAndBillId(debtorEmail, bid);
        /*
        * 判断是否合法
        * 这个人是不是拥有这个债，没有的话报错
        * 这个人有没有权限升级这个债
        * */
        if(debt.isEmpty())
            return new ResponseEntity<String>("No such debt!",HttpStatus.NOT_FOUND);
        InDebt d = debt.get();
        switch (d.getStatus()){
            case 0:
                d.setStatus(1);
                // TODO: add log or push message to user
                break;
            case 1:
                d.setStatus(2);
                break;
            default:
                return new ResponseEntity<String>("No permission to upgrade debt status",HttpStatus.FORBIDDEN);
        }
        inDebtRepository.save(d);
        return new ResponseEntity<String>("Debt status upgraded",HttpStatus.OK);
    }

    @PutMapping("/bill/{bid}/{did}")
    public ResponseEntity<?> UpgradeBillDebtStatus( @PathVariable Integer bid,
                                                    @PathVariable Integer did,
                                                    @RequestHeader(value = "Authorization") String token) {
        // 判断用户是否有权限操作这个账单的内容（判断用户是否拥有这个账单）
        String ownerEmail = controllerHelper.getEmailFromJWT(token);
        if(billRepository.findByOwnerEmailAndBid(ownerEmail, bid).isEmpty()){
            return new ResponseEntity<String>("No such bill",HttpStatus.NOT_FOUND);
        }
        // 判断是否存在这个债
        Optional<InDebt> debt = inDebtRepository.findByDebtorIdAndBillId(did, bid);
        if(debt.isEmpty()){
            return new ResponseEntity<String>("No such debt in the bill",HttpStatus.NOT_FOUND);
        }
        // 判断用户是否有权限操作这个账单
        InDebt d =debt.get();
        if(d.getStatus() != 2)
            return new ResponseEntity<String>("No permission",HttpStatus.FORBIDDEN);
        return new ResponseEntity<String>("Debt status upgraded",HttpStatus.OK);
    }



    private void saveReceipt(MultipartFile receipt) throws IOException {
        String avatarPath = receipt.getOriginalFilename();
        String saveFilePath = receiptSavePath + File.separator + avatarPath;
        receipt.transferTo(new File(saveFilePath));
    }

}
