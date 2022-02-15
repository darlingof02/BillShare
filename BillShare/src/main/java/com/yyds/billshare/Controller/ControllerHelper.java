package com.yyds.billshare.Controller;


import com.yyds.billshare.Model.User;
import com.yyds.billshare.Repository.UserRepository;
import com.yyds.billshare.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ControllerHelper {
    @Autowired
    private UserRepository userJpaRepository;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public User getUserByEmail(String email){
        List<User> users = userJpaRepository.findByEmail(email);
        if(users==null || users.isEmpty())
            throw new UsernameNotFoundException(String.format("USER_NOT_FOUND '%s'.", email));
        return users.get(0);
    }
    public User getUserFromJWT(String jwtToken){
        String email = jwtTokenUtil.getUsernameFromToken(jwtToken);
        return getUserByEmail(email);
    }
    public String getEmailFromJWT(String jwtToken){
        return jwtTokenUtil.getUsernameFromToken(jwtToken);
    }


}
