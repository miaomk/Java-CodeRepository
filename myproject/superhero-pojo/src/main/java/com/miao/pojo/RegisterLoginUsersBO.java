package com.miao.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户登录/注册实体类
 *
 * @author miao
 */
@ApiModel(value = "用户对象", description = "从客户端，由用户传入的数据封装在此entity中")
@Data
public class RegisterLoginUsersBO {

    @ApiModelProperty(value = "用户名", name = "username", example = "jack", required = true)
    private String username;

    @ApiModelProperty(value = "密码", name = "password", example = "123456", required = true)
    private String password;

}