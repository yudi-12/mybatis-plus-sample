package org.yudi;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.yudi.entity.User;
import org.yudi.mapper.UserMapper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MapperCURDTest {

    @Autowired
    private UserMapper userMapper;

    // ============================Insert============================

    @Test
    public void insert(){
        User user = new User(30L, "yudi-30", 27, "13127085927");
        int i = userMapper.insert(user);
    }

    // ============================Delete============================

    @Test
    public void delete(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id", 30L);
        int i = userMapper.delete(wrapper);
    }

    @Test
    public void deleteById(){
        int i = userMapper.deleteById(29L);
    }

    @Test
    public void deleteBatchIds(){
        Long[] ids = {27L, 28L};
        int i = userMapper.deleteBatchIds(Arrays.stream(ids).collect(Collectors.toList()));
    }

    @Test
    public void deleteByMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("id", 26L);
        int i = userMapper.deleteByMap(map);
    }

    // ============================Update============================

    @Test
    public void update(){
        User user = new User();
        user.setName("peiyu");
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id", 25L);
        // UPDATE user SET name="peiyu" WHERE (id = 25)
        int i = userMapper.update(user, wrapper);
    }

    @Test
    public void updateById(){
        User user = new User();
        user.setId(24L);
        user.setName("tom");
        user.setAge(20);
        int i = userMapper.updateById(user);
    }

    // ============================Select============================

    @Test
    public void selectById(){
        User user = userMapper.selectById(24L);
        System.out.println(user);
    }

    @Test
    public void selectOne(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name", "yudi-23");
        User user = userMapper.selectOne(wrapper);
        System.out.println(user);
    }

    @Test
    public void selectBatchIds(){
        Long[] ids = {1L, 2L, 3L};
        List<User> list = userMapper.selectBatchIds(Arrays.stream(ids).collect(Collectors.toList()));
        list.forEach(System.out::println);
    }

    @Test
    public void selectList(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name", "yudi");
        List<User> list = userMapper.selectList(wrapper);
        list.forEach(System.out::println);
    }

    @Test
    public void selectByMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("name", "yudi");
        List<User> list = userMapper.selectByMap(map);
        list.forEach(System.out::println);
    }

    @Test
    public void selectMaps(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name", "yudi");
        List<Map<String, Object>> maps = userMapper.selectMaps(wrapper);
        for (Map<String, Object> map : maps){
            System.out.println("=====@@=====");
            for (String key : map.keySet()){
                System.out.println("key：" + key + "，value：" + map.get(key));
            }
        }
    }

    @Test
    public void selectObjs(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name", "yudi");
        List<Object> list = userMapper.selectObjs(wrapper);
        list.forEach(System.out::println);
    }

    // ============================Page需要搭配分页插件进行使用============================

    @Test
    public void selectPage(){
        Page<User> page = new Page<>(1, 5);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name", "yudi");
        Page<User> result = userMapper.selectPage(page, wrapper);
        // 总页数 -> 4
        System.out.println(result.getPages());
        // 当前页 -> 1
        System.out.println(result.getCurrent());
        // 总条数 -> 17
        System.out.println(result.getTotal());
        // 每页条数 -> 5
        System.out.println(result.getSize());
        // 分页数据
        result.getRecords().stream().forEach(System.out::println);
    }

    @Test
    public void selectMapsPage(){
        Page page = new Page<>(1, 5);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name", "yudi");
        Page result = userMapper.selectMapsPage(page, wrapper);
        List<Map<String, Object>> list = result.getRecords();
        for(Map<String, Object> map : list){
            System.out.println("=====@@=====");
            for (String key : map.keySet()){
                System.out.println("key：" + key + "，value：" + map.get(key));
            }
        }
    }

    // ============================Count============================

    @Test
    public void selectCount(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name", "yudi");
        Long count = userMapper.selectCount(wrapper);
        System.out.println(count);
    }

}
