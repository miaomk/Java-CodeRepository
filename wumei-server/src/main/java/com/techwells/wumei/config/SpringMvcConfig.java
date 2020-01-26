package com.techwells.wumei.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 陈加兵 springmvc的配置类
 */
@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

	// 注入返回Json格式的日期格式
	// @Bean
	// public MappingJackson2HttpMessageConverter
	// mappingJackson2HttpMessageConverter(){
	// ObjectMapper objectMapper=new ObjectMapper(); //创建ObjectMapper
	// objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	// //指定返回Json数据的日期格式
	// MappingJackson2HttpMessageConverter converter=new
	// MappingJackson2HttpMessageConverter();//创建转化器
	// converter.setObjectMapper(objectMapper);
	// return converter;
	// }

	// 添加跨域的功能
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE",
								"OPTIONS").allowCredentials(true).maxAge(3600);
				; // 对所有的路径都支持跨域的访问
			}
		};
	}

	// @Bean
	// public ServletRegistrationBean dispatcherRegistration(DispatcherServlet
	// dispatcherServlet) {
	// ServletRegistrationBean registration = new ServletRegistrationBean(
	// dispatcherServlet);
	// registration.addUrlMappings("*.do");
	// return registration;
	// }
	//

	// @Bean
	// public PathMatchConfigurer configurePathMatch() {
	// PathMatchConfigurer configurer=new PathMatchConfigurer();
	// configurer.setUseSuffixPatternMatch(false)
	// .setUseTrailingSlashMatch(true);
	// return configurer;
	// }

	/**
	 * 配置开启路径后缀匹配规则
	 */
	// @Override
	// public void configurePathMatch(PathMatchConfigurer configurer) {
	// // 开启路径后缀匹配
	// configurer.setUseRegisteredSuffixPatternMatch(true);
	// }
	//
	// /**
	// * 设置匹配*.do后缀请求
	// *
	// * @param dispatcherServlet
	// * @return
	// */
	// @Bean
	// public ServletRegistrationBean servletRegistrationBean(
	// DispatcherServlet dispatcherServlet) {
	// ServletRegistrationBean<DispatcherServlet> servletServletRegistrationBean
	// = new ServletRegistrationBean<>(
	// dispatcherServlet);
	// servletServletRegistrationBean.addUrlMappings("*.do");
	// return servletServletRegistrationBean;
	// }

}
