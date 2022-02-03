package com.yyds.billshare;

import com.yyds.billshare.Model.Bill;
import com.yyds.billshare.Model.User;
import com.yyds.billshare.Repository.BillRepository;
import com.yyds.billshare.Repository.DebtorRepository;
import com.yyds.billshare.Repository.UserRepository;
import org.apache.catalina.filters.ExpiresFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
//@SpringBootTest
public class testshit {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BillRepository billRepository;
    @Test
    public void testBill(){
        // create 3 bills and 1 user
        // 1. let user add 3 bills
        // 2. let bills add user
        User u1 = new User(null,"Yuning","Xie","4_Celery","xieyn12345@gmail.com",null,
                "122345",9499927197L);
        System.out.println(u1.getBills().size());

        Bill b1 = new Bill(null,u1,10,"shit",0,null,null,"shit","pay now",null);
        Bill b2 = new Bill(null,u1,20,"shit",1,null,null,"shit","pay now",null);
        Bill b3 = new Bill(null,u1,30,"shit",3,null,null,"shit","pay now",null);
        System.out.println(b1.getOwner());
        b1.setOwner(u1);
        b1.setOwner(u1);
        b1.setOwner(u1);
        System.out.println(u1.getBills().size());
        entityManager.persist(u1);
        entityManager.persist(b1);
        entityManager.persist(b2);
        entityManager.persist(b3);

        List<User> users = userRepository.findByEmail("xieyn12345@gmail.com");
        List<Bill> bills = billRepository.findByAmount(10);

        System.out.println("++++++++++");
        System.out.println(users);
        System.out.println(bills);

    }

    @Test
    public void testDebtor(){
        // create 1 bills and 1 user
        // create 3 debtors
        // 2. let bills add user

    }
}
