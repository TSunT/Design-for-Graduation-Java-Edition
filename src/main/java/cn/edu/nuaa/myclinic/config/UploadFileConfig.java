package cn.edu.nuaa.myclinic.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;
@Configuration
public class UploadFileConfig {
    @Value("${file.uploadFolder}")
    private String uploadFolder;

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation(uploadFolder);
        //文件最大
        factory.setMaxFileSize(DataSize.parse("5MB"));
        // 设置总上传数据总大小
        factory.setMaxRequestSize(DataSize.parse("10MB"));
        return factory.createMultipartConfig();
    }
    //https://blog.csdn.net/xiuyuandashen/article/details/111042344
}
