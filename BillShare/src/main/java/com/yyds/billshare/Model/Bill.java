package com.yyds.billshare.Model;


import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
    private Timestamp creatTime;
    @Column(name = "finish_time")
    private Timestamp finishTime;

    private String type;
    private String comment;

    @OneToMany(mappedBy = "bill")
    @ToString.Exclude
    private Set<Debtor> debtors = new HashSet<>();


    public Bill(Integer bid, User owner, int amount, String receipt, int status, Timestamp creatTime, Timestamp finishTime, String type, String comment) {
        this.bid = bid;
        this.owner = owner;
        this.amount = amount;
        this.receipt = receipt;
        this.status = status;
        this.creatTime = creatTime;
        this.finishTime = finishTime;
        this.type = type;
        this.comment = comment;
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
