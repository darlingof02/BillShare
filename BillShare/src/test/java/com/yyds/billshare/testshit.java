package com.yyds.billshare;

import com.yyds.billshare.Model.Bill;
import com.yyds.billshare.Model.Debtor;
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
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Date;
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
    @Autowired
    private DebtorRepository debtorRepository;
    @Test
    public void testBill(){
        // conclusion: better save many-side than save one-side to maintain the relation

        // create 3 bills and 1 user
        // 1. let user add 3 bills
        // 2. let bills add user
        User u1 = new User(null,"Yuning","Xie","4_Celery","xieyn12345@gmail.com",null,
                "122345",9499927197L);
        System.out.println(u1.getBills().size());

        Bill b1 = new Bill(null,null,10,"shit",0,null,null,"shit","pay now");
        Bill b2 = new Bill(null,null,20,"shit",1,null,null,"shit","pay now");
        Bill b3 = new Bill(null,null,30,"shit",3,null,null,"shit","pay now");
        System.out.println(b1.getOwner());
        u1.getBills().add(b1);
        u1.getBills().add(b2);
        u1.getBills().add(b3);
        entityManager.persist(u1);

        System.out.println(u1.getBills().size());


//        b1.setOwner(u1);
//        b1.setOwner(u1);
//        b1.setOwner(u1);
//        entityManager.persist(b1);
//        entityManager.persist(b2);
//        entityManager.persist(b3);

        List<User> users = userRepository.findByEmail("xieyn12345@gmail.com");
        List<Bill> bills = billRepository.findByAmount(10);
        List<Bill> allbills = billRepository.findAll();

        System.out.println("++++++++++");
        System.out.println(users);
        System.out.println(bills);
        System.out.println(allbills);

    }

    @Test
    public void testDebtor(){
        // create 1 bills and 1 user
        // create 3 debtors
        // 2. let bills add user
        Date date = new Date();
        User u1 = new User(null,"Yuning","Xie","4_Celery","test@gmail.com",null,
                "122345",9499927197L);
        User u2 = new User(null,"Yizhuang","Peng","darlingof2","peng@gmail.com",null,
                "122345",9499927197L);
        Bill b1 = new Bill(null,u1,10,"shit",0,null,null,"shit","pay now",null);
        Debtor d1 = new Debtor(u2,b1,0, new Timestamp(date.getTime()),null,10);
        Debtor d2 = new Debtor(u1,b1,0,null,null,20);

        entityManager.persist(u1);
        entityManager.persist(u2);
        entityManager.persist(b1);
        entityManager.persist(d1);
        entityManager.persist(d2);
        List<Debtor> ds = debtorRepository.findByAmount(20);
        System.out.println("++++");
        System.out.println(ds);


    }


    @Test
    public void testCreateBill(){


    }
}
