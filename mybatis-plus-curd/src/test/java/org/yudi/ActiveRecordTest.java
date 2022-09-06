package org.yudi;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.yudi.entity.User;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ActiveRecordTest {

    // ============================Insert============================

    @Test
    public void insert(){
        User user = new User();
        user.setId(26L);
        user.setName("jack");
        user.setAge(28);
        boolean b = user.insert();
    }

    @Test
    public void insertOrUpdate(){
        User user = new User();
        user.setId(26L);
        user.setName("cat");
        user.setAge(29);
        boolean b = user.insertOrUpdate();
    }

    // ============================Delete============================

    @Test
    public void delete(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.in("id", 25L, 26L);
        User user = new User();
        boolean b = user.delete(wrapper);
    }

    @Test
    public void deleteById(){
        User user = new User();
        user.setId(24L);
        boolean b1 = user.deleteById();

        boolean b2 = user.deleteById(23L);
    }

    // ============================Update============================

    @Test
    public void update(){
        User user = new User();
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", 21L);
        wrapper.set("name", "jack");
        boolean b = user.update(wrapper);
    }

    @Test
    public void updateById(){
        User user = new User();
        user.setId(22L);
        user.setName("tom");
        boolean b = user.updateById();
    }

    // ============================Select============================

    @Test
    public void selectAll(){
        User user = new User();
        user.setName("yudi");
        user.selectAll().forEach(System.out::println);
    }

    @Test
    public void selectById(){
        User user = new User();
        user.setId(22L);

        User User = user.selectById();
        System.out.println(User);

        User user3 = user.selectById(21L);
        System.out.println(user3);
    }

    @Test
    public void selectList(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name", "yudi");

        User user = new User();
        user.selectList(wrapper).forEach(System.out::println);
    }

    @Test
    public void selectOne(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name", "yudi");

        User user = new User();
        // 如果结果是多个，不会报错，默认取第一个
        User User = user.selectOne(wrapper);
        System.out.println(User);
    }

    // ============================Page============================

    @Test
    public void selectPage(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name", "yudi");
        Page<User> page = new Page<>(1, 2);

        User user = new User();
        // 如果结果是多个，不会报错，默认取第一个
        Page<User> result = user.selectPage(page, wrapper);
        // 总页数 -> 9
        System.out.println(result.getPages());
        // 当前页 -> 1
        System.out.println(result.getCurrent());
        // 总条数 -> 17
        System.out.println(result.getTotal());
        // 每页条数 -> 2
        System.out.println(result.getSize());
        // 分页数据
        result.getRecords().stream().forEach(System.out::println);
    }

    // ============================Count============================

    @Test
    public void selectCount(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name", "yudi");

        User user = new User();
        long count = user.selectCount(wrapper);
        System.out.println(count);
    }

}
