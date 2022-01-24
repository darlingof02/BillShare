package com.yyds.billshare.Controller;



import com.yyds.billshare.Model.User;
import com.yyds.billshare.Repository.UserJpaRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RestController
public class SignUpController {
    private final UserJpaRepository userJpaRepository;

    public SignUpController(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }


    @PostMapping("/create_user")
    private User createUser(@RequestBody User user){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        return userJpaRepository.save(user);
    }

}
