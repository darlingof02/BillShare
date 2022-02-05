package com.yyds.billshare.Model;


import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "indebt")
@IdClass(DebtorPK.class)
@AllArgsConstructor
@NoArgsConstructor
public class InDebt {
    @Id
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "did", referencedColumnName = "uid", nullable = false)
    private User debtor;

    @Id
    @ManyToOne(targetEntity = Bill.class)
    @JoinColumn(name = "bid",referencedColumnName = "bid",nullable = false)
    private Bill bill;

    private int status;
    @Column(name = "accept_time")
    private Date acceptTime;

    @Column(name = "pay_time")
    private Date payTime;

    private int amount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        InDebt inDebt1 = (InDebt) o;
        return debtor != null && Objects.equals(debtor, inDebt1.debtor)
                && bill != null && Objects.equals(bill, inDebt1.bill);
    }

    @Override
    public int hashCode() {
        return Objects.hash(debtor, bill);
    }
}
