package com.yyds.billshare.Model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.yyds.billshare.Model.Form.BillCreateForm;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.*;

@Getter
@Setter
@ToString
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class Bill {
    @Id
    @GeneratedValue
    private Integer bid;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "oid", referencedColumnName = "uid")
    private User owner;

    private int amount;
    private String receipt;
    private int status;

    @Column(name = "create_time")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date createTime;
    @Column(name = "finish_time")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date finishTime;

    private String type;
    private String comment;

    @OneToMany(mappedBy = "bill")
    @ToString.Exclude
    private List<InDebt> inDebts = new ArrayList<>();


    public Bill(Integer bid, User owner, int amount, String receipt, int status, Timestamp createTime, Timestamp finishTime, String type, String comment) {
        this.bid = bid;
        this.owner = owner;
        this.amount = amount;
        this.receipt = receipt;
        this.status = status;
        this.createTime = createTime;
        this.finishTime = finishTime;
        this.type = type;
        this.comment = comment;
    }

    public Bill(BillCreateForm form) {
        this.amount = form.getAmount();
        if(form.getReceipt()!=null)
            this.receipt = form.getReceipt().getOriginalFilename();
        this.status = 0;
        this.createTime = form.getCreateTime();
        this.finishTime = form.getDueTime();
        this.type = form.getType();
        this.comment = form.getComment();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Bill bill = (Bill) o;
        return bid != null && Objects.equals(bid, bill.bid);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
