package com.yyds.billshare.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class DebtorPK implements Serializable {
    private Integer debtor;
    private Integer bill;
}
