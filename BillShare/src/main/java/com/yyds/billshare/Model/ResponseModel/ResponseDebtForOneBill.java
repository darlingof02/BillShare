package com.yyds.billshare.Model.ResponseModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yyds.billshare.Model.Bill;
import com.yyds.billshare.Model.InDebt;
import com.yyds.billshare.Model.User;
import lombok.Data;

import java.util.Date;

/**
 * Actual Response to Frontend when required indebt for the bill
 */
@Data
public class ResponseDebtForOneBill {
    private String debtorEmail;
    private String debtorAvatar;
    private String debtorNickName;
    private String debtorFirstName;
    private String debtorLastName;
    private Long debtorTel;
    private Integer amount;
    private Integer status;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date acceptTime;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date payTime;
    private Integer debtorId;
//    private String comment;
//    private Integer totalAmount;
//    @JsonFormat(pattern="yyyy-MM-dd")
//    private Date createTime;
//    @JsonFormat(pattern="yyyy-MM-dd")
//    private Date due;
//    private String receipt;
//    private String type;

    public ResponseDebtForOneBill(User debtor, int amount, Date acceptTime, Date payTime, int status) {
        this.acceptTime = acceptTime;
        this.payTime = payTime;
        this.status = status;
        this.amount = amount;

        this.debtorNickName = debtor.getNickname();
        this.debtorFirstName = debtor.getFirstname();
        this.debtorLastName = debtor.getLastname();
        this.debtorEmail = debtor.getEmail();
        this.debtorTel = debtor.getTel();
        this.debtorAvatar = debtor.getAvatar();
        this.debtorId = debtor.getUid();
    }



}
