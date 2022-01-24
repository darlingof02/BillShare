package com.yyds.billshare.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bill {
    private int bid;
    private int amount;
    private int receipt;
    private int status;
    private int type;

//    public Bill(int bid, int amount, int receipt, int status, int type) {
//        this.bid = bid;
//        this.amount = amount;
//        this.receipt = receipt;
//        this.status = status;
//        this.type = type;
//    }
//
//    public Bill() {
//    }
//
//    public int getBid() {
//        return bid;
//    }
//
//    public int getAmount() {
//        return amount;
//    }
//
//    public int getReceipt() {
//        return receipt;
//    }
//
//    public int getStatus() {
//        return status;
//    }
//
//    public int getType() {
//        return type;
//    }
//
//    public void setBid(int bid) {
//        this.bid = bid;
//    }
//
//    public void setAmount(int amount) {
//        this.amount = amount;
//    }
//
//    public void setReceipt(int receipt) {
//        this.receipt = receipt;
//    }
//
//    public void setStatus(int status) {
//        this.status = status;
//    }
//
//    public void setType(int type) {
//        this.type = type;
//    }
}
