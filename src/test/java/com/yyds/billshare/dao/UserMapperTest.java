package com.yyds.billshare.dao;

import com.yyds.billshare.MybatisUtil;
import com.yyds.billshare.pojo.User;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {
    @Test
    public void test() {
        SqlSession sqlSession = MybatisUtil.getSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
//        List<User> userList = userMapper.queryAllUsers();
//        for (User user : userList) {
//            System.out.println(user);
//        }
        sqlSession.close();
    }


}