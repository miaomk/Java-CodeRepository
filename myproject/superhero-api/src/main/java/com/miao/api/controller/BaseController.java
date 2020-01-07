package com.miao.api.controller;

import com.miao.common.utils.redis.RedisOperator;
import com.miao.pojo.Users;
import com.miao.pojo.vo.UsersVO;
import org.apache.commons.lang.ArrayUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * 基本类
 *
 * @author miao
 */
@RestController
public class BaseController {

    @Resource
    public RedisOperator redisOperator;

    @Resource
    public Sid sid;

    public static final String REDIS_USER_TOKEN = "redis-user-token";

    public Integer[] getGuessULikeArray(Integer counts) {

        Integer[] guessULikeArray = new Integer[5];
        for (int i = 0; i < guessULikeArray.length; i++) {

            int numIndex = (int) (Math.random() * counts);
            if (ArrayUtils.contains(guessULikeArray, numIndex)) {
                i--;
                continue;
            }
            guessULikeArray[i] = numIndex;
        }

        return guessULikeArray;
    }

    public UsersVO uniqueUserToken(Users users) {
        // 保存用户的分布式会话 - 生成一个token保存到redis中，可以被任何分布式集群节点访问到
        String userId = users.getId();
        //生成一个token
        String uniqueToken = UUID.randomUUID().toString().trim();
        //保存用户会话
        redisOperator.set(REDIS_USER_TOKEN + "：" + userId, uniqueToken);

        //赋值
        UsersVO userVO = new UsersVO();
        BeanUtils.copyProperties(users,userVO);
        userVO.setUserUniqueToken(uniqueToken);

        return userVO;
    }

}
