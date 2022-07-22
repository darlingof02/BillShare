package com.yyds.billshare.Model.ClearModels;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class ClearInfo {
    @Id
    @GeneratedValue
    private Integer cid;

    // TODO: 方向怎么选, 名字怎么选
    @Column(name = "uid1", nullable = false)
    private Integer uid1;

    @Column(name = "uid2", nullable = false)
    private Integer uid2;

    @Column(name = "total_amount", nullable = false)
    private Integer totalAmount;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "create_time")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date createTime;

    @Column(name = "finish_time")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date finishTime;

    @OneToMany(mappedBy = "clearInfo", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<ClearItems> clearItems = new ArrayList<>();

}
