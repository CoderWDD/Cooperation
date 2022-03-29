package com.example.cooperationproject.dbTest;


import com.example.cooperationproject.pojo.User;
import com.example.cooperationproject.mapper.UserMapper;
import com.example.cooperationproject.utils.MyJwtUtil;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@MapperScan("com.example.cooperationproject.mapper")

public class connectiveTest {
//    @Autowired
//    UserMapper userMapper;
//    @Test
//    public void testDBConnective(){
//        List<User> users = userMapper.selectList(null);
//        System.out.println(users);
//    }
//
//    @Test
//    public void testToken(){
//        Map<String,Object> map = new HashMap<>();
//        map.put("userId","CoderWdd");
//        map.put("password","123456");
//        String token = MyJwtUtil.generateToken(map,"CoderWdd");
//        Claims claims = MyJwtUtil.parseToken(token);
////        Map<String,Object> claims = (Map<String, Object>) MyJwtUtil.parseToken(token);
//        System.out.println(claims.get("userId"));
//        System.out.println(claims.get("password"));
//    }
}
