package com.yyds.billshare.Model.ResponseModel;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ResponseDebtsByDebtor {
    private int did;
    private int amount;
    private int bid;
    private int status;
    private Date due;
    private String oname;
}
