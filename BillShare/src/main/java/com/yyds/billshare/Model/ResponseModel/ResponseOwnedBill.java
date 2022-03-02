package com.yyds.billshare.Model.ResponseModel;


import com.yyds.billshare.Model.Bill;
import lombok.Data;

import java.util.Date;

@Data
public class ResponseOwnedBill {
    private Integer bid;
    private Integer status;
    private Date due;
    private Integer amount;
    private String ownerEmail;

    public ResponseOwnedBill(Bill bill) {
        bid = bill.getBid();
        status = bill.getStatus();
        due = bill.getFinishTime();
        amount = bill.getAmount();
        ownerEmail = bill.getOwner().getEmail();
    }
}
