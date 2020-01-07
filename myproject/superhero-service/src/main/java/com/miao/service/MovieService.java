package com.miao.service;

import com.miao.pojo.Movie;

import java.util.List;

/**
 * 电影预告Service
 *
 * @author miao
 */
public interface MovieService {
    /**
     * 查询热门电影预告片
     * 评分越高，越热门
     * 点赞数越高，越热门
     *
     * @param type 类型
     * @return List
     */
    List<Movie> getHotSuperHero(String type);

    /**
     * 查询热门预告数
     *
     * @return
     */
    int queryAllTrailerCounts();

    /**
     * 查询电影记录
     *
     * @return List
     */
    List<Movie> getAllTrailerList();
}
