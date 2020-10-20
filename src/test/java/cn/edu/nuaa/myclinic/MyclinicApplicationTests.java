package cn.edu.nuaa.myclinic;

import cn.edu.nuaa.myclinic.pojo.User;
import cn.edu.nuaa.myclinic.service.AdminService;
import cn.edu.nuaa.myclinic.service.UserSecurityService;

import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MyclinicApplicationTests {
    @Autowired
    UserSecurityService userSecurityService;
    @Autowired
    AdminService adminService;
    @Test
    void contextLoads() {
        userSecurityService.test();
    }
    @Test
    void testPage(){
        List<User> userList = adminService.findAllUser(6, 1);
        PageInfo pageInfo = new PageInfo(userList);
        System.out.println(userList);
        System.out.println(pageInfo);
    }
}
