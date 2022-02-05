package com.yyds.billshare.jwt;

import com.yyds.billshare.Model.User;
import com.yyds.billshare.Repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JwtUserDetailsService  implements UserDetailsService {

    private final UserRepository userRepository;

    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> users = userRepository.findByEmail(username);
        if(users==null || users.isEmpty())
            throw new UsernameNotFoundException(String.format("USER_NOT_FOUND '%s'.", username));
        User user = users.get(0);
        return new JwtUserDetails(user.getUid(),user.getEmail(),user.getPassword(),"ROLE_USER_2");

    }

}
