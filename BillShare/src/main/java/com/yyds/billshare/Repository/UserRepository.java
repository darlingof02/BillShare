package com.yyds.billshare.Repository;


import com.yyds.billshare.Model.ResponseModel.ResponseUserInfo;
import com.yyds.billshare.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    //used to check user's information
    List<User> findByEmail(String email);
    Boolean existsByEmail(String email);

    //used to return user information to frontend
    @Query("SELECT u FROM User as u WHERE u.email =?1")
    List<ResponseUserInfo> findByUserEmail(String email);



}
