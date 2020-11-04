package cn.edu.nuaa.myclinic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class MyclinicApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyclinicApplication.class, args);
    }

}
