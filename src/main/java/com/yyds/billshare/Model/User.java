package com.yyds.billshare.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "user_name" }) })
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long user_id;

    @Column(name = "user_name")
    private String username;
    private String password;

}