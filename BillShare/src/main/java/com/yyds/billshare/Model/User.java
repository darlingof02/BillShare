package com.yyds.billshare.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "email" }) })
@AllArgsConstructor
@NoArgsConstructor
public class User {
    public User(Integer uid, String firstname, String lastname, String nickname, String email, String avatar, String password, Long tel) {
        this.uid = uid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.nickname = nickname;
        this.email = email;
        this.avatar = avatar;
        this.password = password;
        this.tel = tel;
    }

    @Id
    @GeneratedValue
    private Integer uid;

    private String firstname;
    private String lastname;

    private String nickname;
    private String email;
    private String avatar;
    private String password;
    private Long tel;

    @OneToMany(mappedBy = "owner")
    private List<Bill> bills = new ArrayList<>();

}