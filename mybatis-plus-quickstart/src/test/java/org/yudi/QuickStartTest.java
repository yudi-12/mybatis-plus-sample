package org.yudi;

import org.assertj.core.api.ListAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.yudi.entity.User;
import org.yudi.mapper.UserMapper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class QuickStartTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void listUser(){
        List<User> list = userMapper.selectList(null);
        assertThat(list).isNotEmpty();
        list.forEach(System.out::println);
    }
}
