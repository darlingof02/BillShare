package com.yyds.billshare.Model.Form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class UserEditInfoForm {
    @NotEmpty
    @Size(max = 30)
    private String firstname;

    @NotEmpty
    @Size(max = 30)
    private String lastname;

    @NotEmpty
    @Size(max = 30)
    private String nickname;

    private Long tel;

    private MultipartFile avatar;

}
