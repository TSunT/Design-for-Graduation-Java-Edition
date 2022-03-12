package cn.edu.nuaa.myclinic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class CrosConfig implements WebMvcConfigurer {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //corsConfiguration.addAllowedOrigin("http://localhost:8000");
        corsConfiguration.addAllowedOriginPattern("http://192.168.31.*:[8000]");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addExposedHeader(HttpHeaders.COOKIE);
        corsConfiguration.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                //.allowedOrigins("http://localhost:8000")
                //.allowedOrigins("http://192.168.31.17:8000","http://192.168.124.5:8000")
                .allowedOriginPatterns("http://192.168.31.*:[8000]")
                .allowedMethods("GET","HEAD","POST","PUT","DELETE","OPTION")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }
}
