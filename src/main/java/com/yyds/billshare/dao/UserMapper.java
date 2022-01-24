package com.yyds.billshare.dao;

import com.yyds.billshare.pojo.User;

import java.util.*;

public interface UserMapper {
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
