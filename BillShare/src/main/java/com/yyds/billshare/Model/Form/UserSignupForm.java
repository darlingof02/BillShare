package com.yyds.billshare.Model.Form;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserSignupForm {
    private String firstname;
    private String lastname;
    private String nickname;
    private String email;
    private String password;
    private Long tel;

    private MultipartFile avatar;

}
