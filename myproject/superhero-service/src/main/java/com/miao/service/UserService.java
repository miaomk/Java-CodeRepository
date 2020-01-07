package com.miao.service;

import com.miao.pojo.Users;

/**
 * 用户Service
 *
 * @author miao
 */
public interface UserService {
    /**
     * 通过openId查询用户信息
     *
     * @param openId openId
     * @return Users
     */
    Users getUserByOpenId(String openId);

    /**
     * 用户注册
     *
     * @param users 用户信息
     * @return int
     */
    int insertUsers(Users users);

    /**
     * 通过用户名查询用户信息
     *
     * @param userName 账号
     * @return Users
     */
    Users getUserByUserName(String userName);

    /**
     * 通过用户id查询用户信息
     *
     * @param userId 用户id
     * @return Users
     */
    Users getUserByUserId(String userId);


}
