package org.yudi;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.yudi.entity.User;
import org.yudi.service.UserService;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ServiceCURDTest {

    @Autowired
    private UserService userService;

    // ============================Save============================

    @Test
    public void save(){
        // 因为没有使用主键自增，每次执行需要手动调整
        User user = new User(7L, "yudi", 25, "13127085927@163.com");
        boolean b = userService.save(user);
    }

    @Test
    public void saveBatch(){
        ArrayList<User> list = new ArrayList<>(15);
        for (long i = 8; i < 18; i++) {
            User user = new User(i, "yudi", 25, "13127085927@163.com");
            list.add(user);
        }
        boolean b = userService.saveBatch(list);
    }

    @Test
    public void saveBatchWithBatchSize(){
        ArrayList<User> list = new ArrayList<>(15);
        for (long i = 19; i < 29; i++) {
            User user = new User(i, "yudi-" + i, 25, "13127085927@163.com");
            list.add(user);
        }
        // 批量插入，每批最大为batchSize
        // 如果需要插入的长度为10，batchSize为5，会执行两次，每次插入5条
        boolean b = userService.saveBatch(list, 5);
    }

    @Test
    public void saveOrUpdate(){
        User user = new User(7L, "yudi-7", 25, "13127085927@163.com");
        boolean b1 = userService.saveOrUpdate(user);
        User user2 = new User(29L, "yudi-29", 25, "13127085927@163.com");
        boolean b2 = userService.saveOrUpdate(user2);
    }

    @Test
    public void saveOrUpdateWithUpdateWrapper(){
        // 根据updateWrapper尝试更新，否继续执行saveOrUpdate(T)方法
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.set("age", 27);
        wrapper.set("name", "yudi");

        User user = new User(7L, "yudi-7", 25, "yudi.ltd");
        boolean b1 = userService.saveOrUpdate(user, wrapper);

        User user2 = new User(30L, "yudi-30", 25, "13127085927@163.com");
        boolean b2 = userService.saveOrUpdate(user2, wrapper);
        // updateWrapper一定要指定具体的数据，否则会进行全局修改
        // UPDATE user SET name=?, age=?, email=?, age=?,name=?
    }

    @Test
    public void saveOrUpdateBatch(){
        ArrayList<User> list = new ArrayList<>(15);
        for (long i = 25; i < 35; i++) {
            User user = new User(i, "yudi-" + i, 25, "13127085927@163.com");
            list.add(user);
        }
        userService.saveOrUpdateBatch(list);
    }

    @Test
    public void saveOrUpdateBatchWithBatchSize(){
        // 这里有一个疑问，通过看后台打印的sql
        // 可以看到执行saveOrUpdateBatch时都是先查询当前id是否存在数据，如果没有添加，有则修改
        // 那么这里的batchSize有什么意义吗？
        ArrayList<User> list = new ArrayList<>(15);
        for (long i = 30; i < 40; i++) {
            User user = new User(i, "yudi-" + i, 25, "13127085927@163.com");
            list.add(user);
        }
        userService.saveOrUpdateBatch(list, 5);
    }

    // ============================Remove============================

    @Test
    public void remove(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id", 39L);
        boolean b = userService.remove(wrapper);
    }

    @Test
    public void removeById(){
        boolean b1 = userService.removeById(38L);

        // 即使通过user进行删除，其本质依旧是通过主键，其他信息即使不对也无所谓
        User user = new User(37L, "yudi-7", 25, "yudi.ltd");
        boolean b2 = userService.removeById(user);
    }

    @Test
    public void removeByMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("id", 36L);
        boolean b = userService.removeByMap(map);
    }

    @Test
    public void removeByIds(){
        ArrayList<User> list = new ArrayList<>(15);
        for (long i = 30; i < 40; i++) {
            User user = new User(i, "yudi-" + i, 25, "13127085927@163.com");
            list.add(user);
        }
        List<Long> ids = list.stream().map(User::getId).collect(Collectors.toList());
        boolean b = userService.removeByIds(ids);
    }

    // ============================Update============================

    @Test
    public void update(){
        UpdateWrapper<User> wrapper1 = new UpdateWrapper<>();
        wrapper1.set("name", "tom");
        wrapper1.eq("id", 1L);
        // UPDATE user SET name=? WHERE (id = ?)
        boolean b1 = userService.update(wrapper1);

        User user = new User();
        user.setEmail("qq.com");
        UpdateWrapper<User> wrapper2 = new UpdateWrapper<>();
        wrapper2.in("id", 2,3,4);
        // UPDATE user SET email=? WHERE (id IN (?,?,?))
        boolean b2 = userService.update(user, wrapper2);
    }

    @Test
    public void updateById(){
        User user = new User(29L, "rose", 27, "13894518163");
        // UPDATE user SET name=?, age=?, email=? WHERE id=?
        boolean b = userService.updateById(user);
    }

    @Test
    public void updateBatchById(){
        ArrayList<User> list = new ArrayList<>(15);
        for (long i = 20; i < 30; i++) {
            User user = new User(i, "yudi-" + i, 20, "13127085927");
            list.add(user);
        }
        boolean b = userService.updateBatchById(list);
    }

    @Test
    public void updateBatchByIdWithBatchSize(){
        ArrayList<User> list = new ArrayList<>(15);
        for (long i = 20; i < 30; i++) {
            User user = new User(i, "yudi-" + i, 50, "111");
            list.add(user);
        }
        boolean b = userService.updateBatchById(list, 5);
    }

    // ============================Get============================

    @Test
    public void getById(){
        User user = userService.getById(1L);
        System.out.println(user);
    }

    @Test
    public void getOne(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name", "yudi");
        wrapper.last("LIMIT 1");
        //  结果集如果是多个会抛出异常，随机取一条加上限制条件 wrapper.last("LIMIT 1")
        User user = userService.getOne(wrapper);
        System.out.println(user);
    }

    @Test
    public void getOneWithEx(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name", "yudi");
        // 这里使用name进行查询，结果肯定不只一个
        // 如果throwEx参数为false，则不会抛出异常，默认会取结果集中的第一个作为结果
        User user = userService.getOne(wrapper, false);
        System.out.println(user);
    }

    @Test
    public void getMap(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name", "yudi");
        // getMap不会抛出异常，默认会取第一条数据
        Map<String, Object> map = userService.getMap(wrapper);
        for (String key : map.keySet()){
            System.out.println("key：" + key + "，value：" + map.get(key));
        }
    }

    @Test
    public void getObj(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name", "yudi");
        // 这里返回的不是实体类，而是主键
        Function<Object, String> function = x -> x.toString();
        String str = userService.getObj(wrapper, function);
        System.out.println(str);
    }

    // ============================List============================

    @Test
    public void list(){
        // 查询所有数据
        List<User> list = userService.list();
        list.stream().forEach(System.out::println);
    }

    @Test
    public void listWithWrapper(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name", "yudi");
        List<User> list = userService.list(wrapper);
        list.stream().forEach(System.out::println);
    }

    @Test
    public void listByIds(){
        Long[] ids = {1L, 2L, 3L};
        List<User> list = userService.listByIds(Arrays.stream(ids).collect(Collectors.toList()));
        list.stream().forEach(System.out::println);
    }

    @Test
    public void listByMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("name", "yudi");
        List<User> list = userService.listByMap(map);
        list.stream().forEach(System.out::println);
    }

    @Test
    public void listMaps(){
        List<Map<String, Object>> maps = userService.listMaps();
        for (Map<String, Object> map : maps){
            System.out.println("=======@@=======");
            for (String key : map.keySet()){
                System.out.println("key：" + key + "，value：" + map.get(key));
            }
        }
    }

    @Test
    public void listMapsWithWrapper(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name", "yudi");
        List<Map<String, Object>> maps = userService.listMaps(wrapper);
        for (Map<String, Object> map : maps){
            System.out.println("=======@@=======");
            for (String key : map.keySet()){
                System.out.println("key：" + key + "，value：" + map.get(key));
            }
        }
    }

    @Test
    public void listObjs(){
        List<Object> objects = userService.listObjs();
        // objects中存放的是主键集合而不是实体类
        for (Object object : objects){
            System.out.println(object);
        }
    }

    @Test
    public void listObjsWithMapper(){
        Function<Object, Long> function = x -> Long.parseLong(x.toString());
        List<Long> list = userService.listObjs(function);
        for (Long id : list){
            System.out.println(id);
        }
    }

    @Test
    public void listObjsWithWrapper(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name", "yudi");
        List<Object> list = userService.listObjs(wrapper);
        for (Object obj : list){
            System.out.println(obj);
        }
    }

    @Test
    public void listObjsWithWrapperAndMapper(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name", "yudi");
        List<Long> list = userService.listObjs(wrapper, x -> Long.parseLong(x.toString()));
        for (Long id : list){
            System.out.println(id);
        }
    }

    // ============================Page需要搭配分页插件进行使用============================

    @Test
    public void page(){
        Page<User> page = new Page<>(1, 5);
        Page<User> result = userService.page(page);
        // 总页数 -> 6
        System.out.println(result.getPages());
        // 当前页 -> 1
        System.out.println(result.getCurrent());
        // 总条数 -> 28
        System.out.println(result.getTotal());
        // 每页条数 -> 5
        System.out.println(result.getSize());
        // 分页数据
        result.getRecords().stream().forEach(System.out::println);
    }

    @Test
    public void pageWithWrapper(){
        Page<User> page = new Page<>(2, 5);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name", "yudi");
        Page<User> result = userService.page(page, wrapper);
        result.getRecords().stream().forEach(System.out::println);
    }

    @Test
    public void pageMaps(){
        Page page = new Page(2, 5);
        Page result = userService.pageMaps(page);
        List<Map<String, Object>> list = result.getRecords();
        for (Map<String, Object> map : list){
            System.out.println("=======@@=======");
            for(String key : map.keySet()){
                System.out.println("key：" + key + "，value：" + map.get(key));
            }
        }
    }

    @Test
    public void pageMapsWithWrapper(){
        Page page = new Page(2, 5);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name", "yudi");
        Page result = userService.pageMaps(page, wrapper);
        List<Map<String, Object>> list = result.getRecords();
        for (Map<String, Object> map : list){
            System.out.println("=======@@=======");
            for(String key : map.keySet()){
                System.out.println("key：" + key + "，value：" + map.get(key));
            }
        }
    }

    // ============================Count============================

    @Test
    public void count(){
        long count = userService.count();
        System.out.println(count);
    }

    @Test
    public void countWithWrapper(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name", "yudi");
        long count = userService.count(wrapper);
        System.out.println(count);
    }

    // ============================Chain============================

    @Test
    public void chainQuery(){
        // 这里是一个最基本的使用，后面会单独写一个链式的模块
        List<User> list = userService.lambdaQuery().eq(User::getName, "yudi").list();
        list.stream().forEach(System.out::println);
    }

    @Test
    public void chainUpdate(){
        boolean b = userService.lambdaUpdate().set(User::getAge, 72).eq(User::getId, 1L).update();
    }

}
