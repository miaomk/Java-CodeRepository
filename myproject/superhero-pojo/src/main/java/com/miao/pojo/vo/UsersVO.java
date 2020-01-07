package com.miao.pojo.vo;

import com.miao.pojo.Users;
import lombok.Data;

/**
 * 返回前端的token和用户信息
 *
 * @author miao
 */
@Data
public class UsersVO extends Users {

    private String userUniqueToken;
}
