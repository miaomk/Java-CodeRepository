package com.miao.api.config;

import com.miao.api.controller.interceptor.UserTokenInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * springMvc配置类
 *
 * @author miao
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public UserTokenInterceptor userTokenInterceptor() {
        return new UserTokenInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册拦截器
        registry.addInterceptor(userTokenInterceptor())
                .addPathPatterns("/user/modifyUserInfo");
    }
}
