package cn.edu.nuaa.myclinic;

import cn.edu.nuaa.myclinic.pojo.PatientBrief;
import cn.edu.nuaa.myclinic.pojo.User;
import cn.edu.nuaa.myclinic.service.AdminService;
import cn.edu.nuaa.myclinic.service.UserSecurityService;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.Date;
import java.util.List;
import java.util.Set;

@SpringBootTest
class MyclinicApplicationTests {
    @Autowired
    UserSecurityService userSecurityService;
    @Autowired
    AdminService adminService;
    @Autowired
    RedisTemplate redisTemplate;
    @Test
    void contextLoads() {
        userSecurityService.test();
    }
    @Test
    void testPage(){
        List<User> userList = adminService.findAllUser(6, 1,null);
        PageInfo pageInfo = new PageInfo(userList);
        System.out.println(userList);
        System.out.println(pageInfo);
    }
    @Test
    void testStaffPage(){
        PageInfo allStaff = adminService.findAllStaff(1, 2);
        System.out.println(allStaff);
    }
    @Test
    void testRedis(){
        redisTemplate.opsForValue().set("test","hello redis");
        System.out.println(redisTemplate.opsForValue().get("test"));
        redisTemplate.opsForList().leftPushAll("testlist","哈","楼","java");
        System.out.println(redisTemplate.opsForList().leftPop("testlist"));
        System.out.println(redisTemplate.opsForList().range("testlist", 0, -1));
    }
    @Test
    void testRedis2(){
        redisTemplate.opsForZSet().add("testkey","test1",1);
        redisTemplate.opsForZSet().add("testkey","test2",2);
        redisTemplate.opsForZSet().add("testkey","test3",3);
        redisTemplate.opsForZSet().add("testkey","test4",4);
        redisTemplate.opsForZSet().add("testkey","test5",5);
        System.out.println(redisTemplate.opsForZSet().range("testkey", 0, -1));
        System.out.println(redisTemplate.opsForZSet().rank("testkey", "test3"));
    }
    @Test
    void testRedis3(){
        long time1 = new Date().getTime();
        redisTemplate.opsForZSet().add("testqueue","1first",time1);
        long time2 = new Date().getTime();
        redisTemplate.opsForZSet().add("testqueue","2second",time2);
        redisTemplate.opsForZSet().add("testqueue","3third",new Date().getTime());
        Boolean b = redisTemplate.opsForZSet().add("testqueue", "4fourth", new Date().getTime());
        System.out.println("add four:"+b);
        redisTemplate.opsForZSet().add("testqueue","5fifth",new Date().getTime());
        System.out.println(redisTemplate.opsForZSet().range("testqueue", 0, -1));
        System.out.println(redisTemplate.opsForZSet().rank("testqueue", "3third"));
        System.out.println(time1+"<<"+time2);
    }
    @Test
    void testRedis4(){
        Set depRegistryQueue3 = redisTemplate.opsForZSet().range("depRegistryQueue3", 0, 0);
        Object[] objects = depRegistryQueue3.toArray();
        System.out.println(objects);
        PatientBrief pb=(PatientBrief)objects[0];
        System.out.println(pb);
    }
    @Test
    void testRedis5(){
        Set<ZSetOperations.TypedTuple<PatientBrief>> depRegistryQueue3 = redisTemplate.opsForZSet().rangeWithScores("depRegistryQueue3", 0, 0);
        System.out.println(depRegistryQueue3);
        for (ZSetOperations.TypedTuple<PatientBrief> tuple:depRegistryQueue3){
            PatientBrief value = tuple.getValue();
            System.out.println("value:"+value);
            System.out.println("score:"+tuple.getScore());
        }
    }
}
