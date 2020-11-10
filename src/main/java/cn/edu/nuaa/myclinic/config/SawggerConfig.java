package cn.edu.nuaa.myclinic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration //配置类
@EnableSwagger2// 开启Swagger2的自动配置
public class SawggerConfig {
    //Swagger实例Bean是Docket，所以通过配置Docket实例来配置Swaggger
    @Bean
    public Docket docket(Environment environment){
        // 设置要显示swagger的环境
        Profiles profile = Profiles.of("test","dev");
        // 判断当前是否处于dev、test环境
        // 通过 enable() 接收此参数判断是否要显示
        boolean b = environment.acceptsProfiles(profile);

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                //enable表示是否启用swagger 默认为true
                .enable(b)
                //添加分组，标识一个程序员扫描的包，多个程序员写自己的docket放入ioc中
                .groupName("taotao")
                .select()
                // 通过.select()方法,apis去配置扫描接口,RequestHandlerSelectors配置如何扫描接口
                .apis(RequestHandlerSelectors.basePackage("cn.edu.nuaa.myclinic.controller"))
                // paths表示扫描什么请求路径
                //.paths(PathSelectors.ant("/hello"))
                .build();
    }

    private ApiInfo getApiInfo(){
        Contact contact = new Contact("suntao", "https://nuaa.edu.cn", "suntao2020@nuaa.edu.cn");
        return new ApiInfo(
                "Hospital Information System", // 标题
                "Code by SunTao", // 描述
                "v1.0", // 版本
                "https://nuaa.edu.cn/", // 组织链接
                contact, // 联系人信息
                "Apach 2.0 许可", // 许可
                "许可链接", // 许可连接
                new ArrayList<>()// 扩展
        );
    }
}
