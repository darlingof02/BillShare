package com.yyds.billshare.Controller;
import com.yyds.billshare.Model.Form.UserSignupForm;
import com.yyds.billshare.Model.User;
import com.yyds.billshare.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;

@RestController
@CrossOrigin(origins = "http://localhost:3000/", allowCredentials = "true", allowedHeaders = "*")
public class SignUpController {

    // image save path
    @Value("/Users/yuning/Documents/GitHub/BillShare/BillShare/src/main/resources/static/image")
    private String avatarSavePath;
    private final UserRepository userJpaRepository;
    public SignUpController(UserRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @PostMapping("/create_user")
    private String register (@Valid UserSignupForm form, BindingResult bindingResult) throws IOException {
        if(bindingResult.hasErrors()){
            return bindingResult.getAllErrors().toString();
        }

        if(!form.getAvatar().isEmpty())
            this.saveAvatar(form.getAvatar());
        userJpaRepository.save(new User(form));


        // TODO: should throw exception if the email has been used
        return "register successful";
    }

    //save avatar
    private void saveAvatar(MultipartFile avatar) throws IOException {
        String avatarPath = avatar.getOriginalFilename();
        String saveFilePath = avatarSavePath + File.separator + avatarPath;
        avatar.transferTo(new File(saveFilePath));
    }

    // test
    @GetMapping("/shit")
    private String testCase(){
        return "welcome";
    }

}
