package com.yyds.billshare.Controller;



import com.yyds.billshare.Model.User;
import com.yyds.billshare.Repository.UserJpaRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignUpController {
    private final UserJpaRepository userJpaRepository;

    public SignUpController(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }


    @PostMapping("/create_user")
    private User createUser(@RequestBody User user){
        System.out.println(user);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        System.out.println(user);
        return userJpaRepository.save(user);
    }
    @GetMapping("/test")
    private String testCase(){


        return "welcome";
    }

}
