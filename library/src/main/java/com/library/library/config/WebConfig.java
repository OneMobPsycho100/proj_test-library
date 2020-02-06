package com.library.library.config;

import com.library.library.component.JwtHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置静态资源（图片）访问
 * @author chenmingzhe
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private JwtHandlerInterceptor jwtHandlerInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/image/**")
                .addResourceLocations("E:\\images\\");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtHandlerInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("*/login");
    }
}