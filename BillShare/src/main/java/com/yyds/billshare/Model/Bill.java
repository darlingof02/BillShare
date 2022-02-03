package com.yyds.billshare.Model;


import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Data
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

    @OneToMany(targetEntity = Debtor.class)
    private Set<Debtor> debtors = new HashSet<>();

}
