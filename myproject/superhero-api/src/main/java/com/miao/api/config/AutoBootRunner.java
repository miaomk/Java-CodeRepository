package com.miao.api.config;

import com.alibaba.fastjson.JSON;
import com.miao.common.utils.redis.RedisOperator;
import com.miao.pojo.Movie;
import com.miao.service.MovieService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 自启动类
 *
 * @author miao
 */
@Component
public class AutoBootRunner implements ApplicationRunner {
    @Resource
    private MovieService movieService;
    @Resource
    private RedisOperator redisOperator;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //1.获取所有的电影记录
        List<Movie> allTrailerList = movieService.getAllTrailerList();

        //2.向Redis中存入数据
        for (int i = 0; i < allTrailerList.size(); i++) {
            redisOperator.set("guess-trailer-id:" + i, JSON.toJSONString(allTrailerList.get(i)));
        }
    }
}
