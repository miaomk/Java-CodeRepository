package com.miao.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger 配置类
 *
 * @author miao
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {


    /***
     * swagger2 配置项，可以配置swagger2的一些基本内容以及扫描的api
     * 访问地址：http://ip:port/projectName/swagger-ui.html
     * @return Docket
     */
    @Bean
    public Docket creatRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.miao.api.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 构建文档api的基本信息
     *
     * @return ApiInfo
     */
    private ApiInfo apiInfo() {

        return new ApiInfoBuilder()
                .title("电影预告api接口文档")
                .contact(new Contact("miaomk", "www.qq.com", "15888289135@163.com"))
                .description("电影预告api接口文档的描述信息")
                .version("1.0.1")
                .termsOfServiceUrl("www.qq.com")
                .build();

    }
}
