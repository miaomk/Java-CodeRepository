package com.techwells.wumei;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.techwells.wumei.servlet.CallBackServletPC;
import com.techwells.wumei.servlet.LoginServletPC;

/**
 * springBoot的启动类
 * @author 陈加兵
 */
@EnableTransactionManagement   //开启事务功能，等同于xml配置方式的 <tx:annotation-driven />
@MapperScan(value="com.techwells.wumei.dao")  //扫描dao
@SpringBootApplication
@ComponentScan(value = {"com.techwells"})
@EnableScheduling
//@EnableScheduling //开启定时任务
public class WuMeiServerApplication extends SpringBootServletInitializer  {

	public static void main(String[] args) {
		SpringApplication.run(WuMeiServerApplication.class, args);
	}
		
	//继承SpringBootServletInitializer实现war包的发布
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WuMeiServerApplication.class);
    }
		
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
		return new ServletRegistrationBean(new LoginServletPC(), "/wxLogin/*");// ServletName默认值为首字母小写，即myServlet
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public ServletRegistrationBean servletRegistrationBean1() {
		return new ServletRegistrationBean(new CallBackServletPC(), "/wxCallBack/*");// ServletName默认值为首字母小写，即myServlet
	}
	
	/*@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet){
		ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet);
		registration.getUrlMappings().clear();
		registration.addUrlMappings("*.do");
		registration.addUrlMappings("*.json");
		return registration;
		
	}*/
	
}
