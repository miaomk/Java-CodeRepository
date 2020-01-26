package com.techwells.wumei.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.techwells.wumei.util.AllInterceptor;

@SuppressWarnings("deprecation")
@Configuration
public class CorsConfig extends WebMvcConfigurerAdapter {
	// @Override
	// public void addCorsMappings(CorsRegistry registry) {
	// registry.addMapping("/**").allowedOrigins("*")
	// .allowedMethods("GET", "HEAD", "POST","PUT", "DELETE", "OPTIONS")
	// .allowCredentials(true).maxAge(3600);
	// }
	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AllInterceptor());
        //可以添加其他拦截器
        
        super.addInterceptors(registry);
    }
}
