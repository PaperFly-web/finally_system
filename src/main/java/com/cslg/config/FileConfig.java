package com.cslg.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FileConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/move_web/move_pic/**")
                .addResourceLocations("file:/D:/move_web/move_pic/");
        registry.addResourceHandler("/move_web/move/**")
                .addResourceLocations("file:/D:/move_web/move/");
        registry.addResourceHandler("/move_web/movie/**")
                .addResourceLocations("file:/D:/move_web/movie/");
        registry.addResourceHandler("/move_web/movie_pic/**")
                .addResourceLocations("file:/D:/move_web/movie_pic/");
    }


}