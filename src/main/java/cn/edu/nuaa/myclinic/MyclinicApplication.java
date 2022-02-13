package cn.edu.nuaa.myclinic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.edu.nuaa.myclinic.mapper")
public class MyclinicApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyclinicApplication.class, args);
    }

}
