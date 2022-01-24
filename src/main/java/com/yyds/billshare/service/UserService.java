package com.yyds.billshare.service;

import com.yyds.billshare.pojo.User;

import java.util.List;

public interface UserService {
    //create user
    boolean insertUser(User u);

    //delete user
    boolean deleteUserById(int id);

    //update user
    boolean updateUser(User u);

    //look for user
    User queryUserById(int id);

    //look for all users
    List<User> queryAllUsers();
}
