package com.yyds.billshare.Model.ResponseModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yyds.billshare.Model.Bill;
import com.yyds.billshare.Model.User;
import lombok.Data;

import java.util.Date;

/**
 * Actual response when user require the info of a certain bill
 * (class projection of findByBillId)
 */
@Data
public class ResponseOneBill {
    private Integer ownerId;
    private String ownerNickName;
    private String ownerFirstName;
    private String ownerLastName;
    private Integer totalAmount;
    private Integer status;
    private String receipt;
    private String comment;
    private String type;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date createTime;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date due;


    public ResponseOneBill(Bill bill) {
        this.comment = bill.getComment();
        this.createTime = bill.getCreateTime();
        this.due = bill.getFinishTime();
        this.ownerFirstName = bill.getOwner().getFirstname();
        this.ownerLastName = bill.getOwner().getLastname();
        this.ownerNickName = bill.getOwner().getNickname();
        this.ownerId = bill.getOwner().getUid();
        this.status = bill.getStatus();
        this.totalAmount = bill.getAmount();
        this.type = bill.getType();
        this.receipt = bill.getType();
    }
}
