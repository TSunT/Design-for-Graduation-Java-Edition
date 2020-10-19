package cn.edu.nuaa.myclinic;

import cn.edu.nuaa.myclinic.service.UserSecurityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MyclinicApplicationTests {
    @Autowired
    UserSecurityService userSecurityService;
    @Test
    void contextLoads() {
        userSecurityService.test();
    }

}
