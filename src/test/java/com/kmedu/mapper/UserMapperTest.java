package com.kmedu.mapper;

import com.kmedu.Application;
import com.kmedu.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class )//这里是启动类
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void findByName() {
        User user = userMapper.findByName("admin");
        System.out.println(user);
    }
}