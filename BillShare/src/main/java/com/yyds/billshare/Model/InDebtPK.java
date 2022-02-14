package com.yyds.billshare.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InDebtPK implements Serializable {
    private Integer debtor;
    private Integer bill;
}
