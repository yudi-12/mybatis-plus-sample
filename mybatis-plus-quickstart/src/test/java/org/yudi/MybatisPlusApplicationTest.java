package org.yudi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.yudi.entity.User;
import org.yudi.mapper.UserMapper;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MybatisPlusApplicationTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void listUser(){
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
    }
}
