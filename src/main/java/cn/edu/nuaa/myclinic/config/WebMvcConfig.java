package cn.edu.nuaa.myclinic.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Value("${file.staticAccessPath}")
    private String staticAccessPath;

    @Value("${file.uploadFolder}")
    private String uploadFolder;

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img/");
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/editor.md-master/**").addResourceLocations("classpath:/static/editor.md-master/");

        // 配置 服务器上的地址 localhost:8080/Users/zlf/myBlog/upload/ 映射到本地linux/Mac  /Users/zlf/myBlog/upload/ (需要在前面加 file:)
        registry.addResourceHandler(staticAccessPath+"**").addResourceLocations("file:"+uploadFolder);
        //https://blog.csdn.net/xiuyuandashen/article/details/111042344
        super.addResourceHandlers(registry);
    }
}
