package com.yyds.billshare.Model;

import com.yyds.billshare.Model.Form.UserSignupForm;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "email" }) })
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Integer uid;

    private String firstname;
    private String lastname;
    private String nickname;
    private String email;

    private String password;
    private Long tel;
    private String avatar;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Bill> bills = new ArrayList<>();

    @OneToMany(mappedBy = "debtor", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<InDebt> inDebts = new ArrayList<>();

    public User(UserSignupForm userInfo){
        this.firstname = userInfo.getFirstname();
        this.lastname = userInfo.getLastname();
        this.nickname = userInfo.getNickname();
        this.email = userInfo.getEmail();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.password = encoder.encode(userInfo.getPassword());

        this.tel = userInfo.getTel();
        this.avatar = userInfo.getAvatar().getOriginalFilename();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return uid != null && Objects.equals(uid, user.uid);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}