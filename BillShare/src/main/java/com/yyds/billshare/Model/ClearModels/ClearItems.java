package com.yyds.billshare.Model.ClearModels;


import com.yyds.billshare.Model.Bill;
import com.yyds.billshare.Model.InDebt;
import com.yyds.billshare.Model.User;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@IdClass(ClearItemsPK.class)
public class ClearItems {
    @Id
    @ManyToOne(targetEntity = ClearInfo.class)
    @JoinColumn(name = "cid", referencedColumnName = "cid", nullable = false)
    private ClearInfo clearInfo;

    @Id
    private Integer bid;

    private Integer did;
}
