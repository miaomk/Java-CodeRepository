package com.miao.api;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.CrossOrigin;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author miao
 */
@SpringBootApplication
@MapperScan("com.miao.mapper")
@ComponentScan(value = {"com.miao", "org.n3r.idworker", "com.miao.api"})
@CrossOrigin
@EnableTransactionManagement
public class SuperHeroApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(SuperHeroApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SuperHeroApplication.class);
    }

    @Bean
    public util.IdWorker idWorker() {
        return new util.IdWorker(1, 1);
    }
}
