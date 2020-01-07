package com.miao.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 轮播图实体类
 *
 * @author miao
 */
@Data
public class Carousel implements Serializable {

    private static final long serialVersionUID = -3946734305303957850L;
    @Id
    private String id;

    /**
     * 电影id，页面跳转需要用到
     */
    @Column(name = "movie_id")
    private String movieId;

    /**
     * 图片地址
     */
    private String image;

    /**
     * 排序，从零开始，数字越小优先级越大
     */
    private Integer sort;

    /**
     * 是否展示：
     * 1：展示
     * 0：不展示
     */
    @Column(name = "is_show")
    private Integer isShow;

}