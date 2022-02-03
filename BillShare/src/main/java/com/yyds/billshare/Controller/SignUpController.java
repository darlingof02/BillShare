package com.yyds.billshare.Controller;



import com.yyds.billshare.Model.User;
import com.yyds.billshare.Repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@CrossOrigin(origins = "http://localhost:3000/", allowCredentials = "true", allowedHeaders = "*")
public class SignUpController {
    private final UserRepository userJpaRepository;

    public SignUpController(UserRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @PostMapping("/create_user")
    private User createUser(@RequestParam(value = "firstname") String firstname,
                            @RequestParam(value = "lastname") String lastname,
                            @RequestParam(value = "nickname") String nickname,
                            @RequestParam(value = "password") String password,
                            @RequestParam(value = "email") String email,
                            @RequestParam(value = "tel", required = false) Long tel,
                            @RequestParam(value = "avatar") MultipartFile avatar) throws IOException {


        String avatarPath = avatar.getOriginalFilename();
        User user = new User(0,firstname,lastname,nickname,email,avatarPath,password,tel);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        System.out.println(user);
        this.saveAvatar(avatar);

        // TODO: should throw exception if the email has been used
        return userJpaRepository.save(user);
    }

    //save avatar
    private void saveAvatar(MultipartFile avatar) throws IOException {
        String avatarPath = avatar.getOriginalFilename();

        // TODO: need to change save path
        String savePath = "/Users/yuning/Documents/GitHub/BillShare/BillShare/src/main/resources/static/image";
        String saveFilePath = savePath + File.separator + avatarPath;
        avatar.transferTo(new File(saveFilePath));
    }



    // test
    @GetMapping("/shit")
    private String testCase(){
        return "welcome";
    }

}
