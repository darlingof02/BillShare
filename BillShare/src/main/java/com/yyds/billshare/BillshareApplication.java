package com.yyds.billshare;

import com.yyds.billshare.Model.Bill;
import com.yyds.billshare.Model.User;
import com.yyds.billshare.Repository.BillRepository;
import com.yyds.billshare.Repository.UserRepository;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Timestamp;
import java.util.Date;

@SpringBootApplication
public class BillshareApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(BillshareApplication.class, args);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("password"));

//        BillshareApplication billshareApplication = new BillshareApplication();
//        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
//        context.register(UserRepository.class);

//        Date date = new Date();
//        UserRepository userRepository = run.getBean("userRepository",UserRepository.class);
//        BillRepository billRepository = run.getBean("billRepository",BillRepository.class);
//        User u1 = new User(null,"Yuning","Xie","4_Celery","xieyn1234566@gmail.com",null,
//                "122345",9499927197L);
//
//        Bill b1 = new Bill(null,u1,10,"shit",0, null,null,"shit","pay now",null);
//        Bill b2 = new Bill(null,u1,20,"shit",1, null,null,"shit","pay now",null);
//        Bill b3 = new Bill(null,u1,30,"shit",3, null, new Timestamp(date.getTime()),"shit","pay now",null);
//
//        userRepository.save(u1);
//        billRepository.save(b1);
//        billRepository.save(b2);
//        billRepository.save(b3);
    }


}
