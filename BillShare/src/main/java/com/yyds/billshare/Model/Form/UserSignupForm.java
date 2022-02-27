package com.yyds.billshare.Model.Form;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class UserSignupForm {
    @NotEmpty
    @Size(max = 30)
    private String firstname;

    @NotEmpty
    @Size(max = 30)
    private String lastname;

    @NotEmpty
    @Size(max = 30)
    private String nickname;

    @NotEmpty
    @Email
    private String email;

    @Size(min = 4, max = 30)
    private String password;

    private Long tel;
}
