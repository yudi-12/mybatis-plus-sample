package org.yudi;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.SimpleQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.yudi.entity.User;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SimpleQueryTest{

    // https://blog.csdn.net/weixin_51216079/article/details/124747306

    @Test
    public void test(){
        Map<Long, User> map =
                SimpleQuery.keyMap(new QueryWrapper<User>().eq("id", 5L).lambda(), User::getId);
        for (Long key : map.keySet()){
            System.out.println(key);
            System.out.println(map.get(key));
        }

    }

}
