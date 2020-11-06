package cn.edu.nuaa.myclinic.config;

import cn.edu.nuaa.myclinic.exception.SysExceptionResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SysExceptionResolverConfig {
    @Bean
    public SysExceptionResolver getSysExceptionResolver(){
        return new SysExceptionResolver();
    }
}
