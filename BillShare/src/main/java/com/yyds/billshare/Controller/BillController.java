package com.yyds.billshare.Controller;

import com.yyds.billshare.Model.Bill;
import com.yyds.billshare.Model.InDebt;
import com.yyds.billshare.Model.Form.BillCreateForm;
import com.yyds.billshare.Model.Form.DebtorInfo;
import com.yyds.billshare.Model.ResponseModel.ResponseDebtForOneBill;
import com.yyds.billshare.Model.ResponseModel.ResponseDebtsByDebtor;
import com.yyds.billshare.Model.ResponseModel.ResponseOneBill;
import com.yyds.billshare.Model.ResponseModel.ResponseOwnedBill;
import com.yyds.billshare.Model.User;
import com.yyds.billshare.Repository.BillRepository;
import com.yyds.billshare.Repository.InDebtRepository;
import com.yyds.billshare.WebSocket.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.transaction.annotation.Transactional;
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

    private final BillRepository billRepository;
    private final InDebtRepository inDebtRepository;
    private final ControllerHelper controllerHelper;
    private final WebSocketService websocketService;

    @Autowired
    public BillController(BillRepository billRepository, InDebtRepository inDebtRepository, ControllerHelper controllerHelper, WebSocketService websocketService) {
        this.billRepository = billRepository;
        this.inDebtRepository = inDebtRepository;
        this.controllerHelper = controllerHelper;
        this.websocketService = websocketService;
    }

    // 弃用
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
    // Create bills handler
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
//            websocketService.sendDebtToUser(debtorInfo.getDebtorEmail(), inDebt);
        }
        return new ResponseEntity<String>("Create bill successfully!",HttpStatus.CREATED);
    }


    /**
     * Used to require bills created by user
     * @param token (JWT Token)
     * @return List of ResponseOwnedBill
     */
    @GetMapping("/owned_bills")
    public List<ResponseOwnedBill> getOwnedBills(@RequestHeader(value = "Authorization") String token){
        User owner = controllerHelper.getUserFromJWT(token.substring(7));
        List<ResponseOwnedBill> bills =  billRepository.findByOwnerId(owner.getUid());
//        logger.warn(bills.get(0).toString());
        return bills;
    }
    /**
     * used to get All InDebtor and their Amount for a certain Bill
     * @param bid (id of certain bill that user created)
     * @param token (JWT Token of certain user)
     * @return A list of ResponseDebt
     */
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @GetMapping("/owned_bills/{bid}")
    public ResponseEntity<?> getDebtorsByBill(@PathVariable Integer bid, @RequestHeader(value = "Authorization") String token){
        User owner = controllerHelper.getUserFromJWT(token.substring(7));
        Bill bill = billRepository.getById(bid);
        if(!bill.getOwner().equals(owner)){
            log.warn("Unable to fetch data. " + owner.getEmail() + " has no permission!");
//            throw new RuntimeException();
            return new ResponseEntity<String>("No such bill",HttpStatus.FORBIDDEN);
        }
        List<ResponseDebtForOneBill> indebts = inDebtRepository.findByBill(bill);
        logger.warn(indebts.get(0).toString());
        return new ResponseEntity<>(indebts,HttpStatus.OK);
    }


    @GetMapping("/bills/{bid}")
    public ResponseOneBill getOneBill(@PathVariable Integer bid, @RequestHeader(value = "Authorization") String token) {
        List<ResponseOneBill> bills = billRepository.findByBid(bid);
        logger.warn(bills.get(0).toString());
        return bills.get(0);
    }

    @GetMapping("/debts/{bid}")
    public ResponseEntity<?> getDebtorsByIndebt(@PathVariable Integer bid, @RequestHeader(value = "Authorization") String token) {
        User debtor = controllerHelper.getUserFromJWT(token.substring(7));
        Optional<InDebt> debts = inDebtRepository.findByDebtorIdAndBillId(debtor.getUid(), bid);
        if (debts.isEmpty()) {
            return new ResponseEntity<String>("No such debt!",HttpStatus.NOT_FOUND);
        }

        InDebt debt = debts.get();
        if (debt.getDebtor().getUid() != debtor.getUid()) {
            return new ResponseEntity<String>("No permission to get the bill's information",HttpStatus.FORBIDDEN);
        }

        Bill bill = billRepository.getById(bid);
        List<ResponseDebtForOneBill> indebts = inDebtRepository.findByBill(bill);
        logger.warn(indebts.get(0).toString());
        return new ResponseEntity<>(indebts,HttpStatus.OK);
    }



    @GetMapping("/unarchived_debts")
    public List<ResponseDebtsByDebtor> getDebtsByDid(@RequestHeader(value = "Authorization") String token){
        User debtor = controllerHelper.getUserFromJWT(token.substring(7));
        return inDebtRepository.findResponseDebtsByDebtor(debtor);
    }


//    =========================Upgrade Status==============================

    /**
     * used to refuse a new bill for a debtor
     * @param bid the bid of the new bill
     * @param status the status of the new indebt relation
     * @param token the JWTToken for the debtor
     * @return set the status of the indebt relation to '-1', which means the debtor refuses the new bill
     */
    @PutMapping("debts/decline/{bid}")
    public ResponseEntity<?> DeclineDebt(@PathVariable Integer bid,
                                         @RequestBody String status,
                                         @RequestHeader(value = "Authorization") String token) {
        User debtor = controllerHelper.getUserFromJWT(token);
        Optional<InDebt> debt = inDebtRepository.findByDebtorIdAndBillId(debtor.getUid(), bid);

        if (debt.isEmpty())
            return new ResponseEntity<String>("No such debt!",HttpStatus.NOT_FOUND);
        InDebt d = debt.get();

        if (d.getStatus() != 0)
            return new ResponseEntity<String>("The debt has been accepted!", HttpStatus.BAD_REQUEST);
        d.setStatus(-1);
        inDebtRepository.save(d);
        return new ResponseEntity<String>("The debt has been declined!", HttpStatus.OK);
    }

    @PutMapping("/debts/{bid}")
    public ResponseEntity<?> UpgradeDebtStatus( @PathVariable Integer bid,
                                                @RequestBody String status,
                                                @RequestHeader(value = "Authorization") String token) {
        String debtorEmail = controllerHelper.getEmailFromJWT(token);
        Optional<InDebt> debt = inDebtRepository.findByDebtorEmailAndBillId(debtorEmail, bid);
        /*
        * 判断是否合法
        * 这个人是不是拥有这个债，没有的话报错
        * 这个人有没有权限升级这个债
        * */

        logger.warn("hello world  "+status);
        if(debt.isEmpty())
            return new ResponseEntity<String>("No such debt!",HttpStatus.NOT_FOUND);
        InDebt d = debt.get();
//        if(d.getStatus()!=status)
//            return new ResponseEntity<String>("please refresh",HttpStatus.ACCEPTED);
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

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @PutMapping("/bills/{bid}/{did}")
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
        d.setStatus(3);
        inDebtRepository.save(d);

        Bill bill = billRepository.getById(bid);
        List<ResponseDebtForOneBill> indebts = inDebtRepository.findByBill(bill);
        logger.warn(indebts.get(0).toString());
        return new ResponseEntity<>(indebts,HttpStatus.OK);
    }



    private void saveReceipt(MultipartFile receipt) throws IOException {
        String avatarPath = receipt.getOriginalFilename();
        String saveFilePath = receiptSavePath + File.separator + avatarPath;
        receipt.transferTo(new File(saveFilePath));
    }

}
