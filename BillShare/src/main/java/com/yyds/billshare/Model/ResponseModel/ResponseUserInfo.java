package com.yyds.billshare.Model.ResponseModel;

import com.yyds.billshare.Model.User;
import lombok.Data;

/**
 * Actual response for frontend when requiring User Information
 */
@Data
public class ResponseUserInfo {
    private String firstName;
    private String lastName;
    private String nickName;
    private String email;
    private Long tel;
    private String avatar;
    private Integer uid;

    public ResponseUserInfo(User user) {
        firstName = user.getFirstname();
        lastName = user.getLastname();
        nickName = user.getNickname();
        email = user.getEmail();
        tel = user.getTel();
        uid = user.getUid();
        avatar = user.getAvatar();
    }
}
