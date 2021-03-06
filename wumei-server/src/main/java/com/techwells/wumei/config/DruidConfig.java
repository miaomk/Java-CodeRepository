package com.techwells.wumei.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

/**
 * Druid的配置类
 * 
 * @author 陈加兵
 */
@Configuration
// 指定Druid的数据源的配置类
public class DruidConfig {
	// 配置druid的参数，并且将其注入到容器中
	@ConfigurationProperties(prefix = "spring.datasource")
	@Bean
	public DruidDataSource druid() {
		return new DruidDataSource();
	}

	/**
	 * 配置监控 1、配置一个管理后台的Servlet 2、配置一个监控的filter
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public ServletRegistrationBean statViewServlet() {
		ServletRegistrationBean bean = new ServletRegistrationBean(
				new StatViewServlet(), "/druid/*");
		// 设置初始化参数
		Map<String, Object> initParams = new HashMap<>();
		initParams.put("loginUsername", "admin"); // 设置登录的用户名
		initParams.put("loginPassword", "admin"); // 设置登录的密码
		initParams.put("resetEnable", "false");
		// initParams.put("allow","localhost"); //允许localhost访问，默认是所有都能访问
		// initParams.put("deny","IP地址"); //设置拒绝访问的ip
		bean.setInitParameters(initParams);
		return bean;
	}

	// 配置监控的Filter
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean bean = new FilterRegistrationBean();
		bean.setFilter(new WebStatFilter());
		Map<String, Object> initParams = new HashMap<>();
		initParams.put("exclusions", "*.css,*.js,/druid/*"); // 设置不拦截器的路径
		bean.setInitParameters(initParams);
		bean.setUrlPatterns(Arrays.asList("/**"));
		return bean;
	}
}