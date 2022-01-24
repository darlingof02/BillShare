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
    private Integer uid;

    private String lastname;
    private String firstname;
    private String nickname;
    private String email;
    private String avatar;
    private String password;
    private Long tel;

}