package com.cslg.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");//允许所有跨域请求
//        corsConfiguration.addAllowedOrigin("http://127.0.0.1:5500");//允许http://127.0.0.1:5500可以跨域请求
//        corsConfiguration.addAllowedOrigin("http://paperfly.vipgz1.idcfengye.com");
        corsConfiguration.setAllowCredentials(true);//设置可以使用cookie
        corsConfiguration.addAllowedHeader("*");//放行全部原始头信息
        corsConfiguration.addAllowedMethod("*");//允许所有类型的请求方式
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}