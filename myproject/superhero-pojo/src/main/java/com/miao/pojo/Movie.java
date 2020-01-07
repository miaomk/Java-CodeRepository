package com.miao.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 电影预告实体类
 *
 * @author miao
 */
@Data
public class Movie implements Serializable {

    private static final long serialVersionUID = -3946734305303957850L;
    @Id
    private String id;

    /**
     * 电影名称
     */
    private String name;

    /**
     * 海报
     */
    private String poster;

    /**
     * 电影封面
     */
    private String cover;

    /**
     * 预告片的播放地址
     */
    private String trailer;

    private Float score;

    /**
     * 影片获得赞的数量
     */
    @Column(name = "prised_counts")
    private Integer prisedCounts;

    /**
     * 基本介绍，例：
     * 2018 / 美国 / 科幻 / 超级英雄
     */
    @Column(name = "basic_info")
    private String basicInfo;

    /**
     * 电影原名，例：
     * 原名：Super Man
     */
    @Column(name = "original_name")
    private String originalName;

    /**
     * 上映时间：2018-12-17（中国大陆）
     */
    @Column(name = "release_date")
    private String releaseDate;

    /**
     * 影片时长：189分钟（中国大陆）
     */
    @Column(name = "total_time")
    private String totalTime;

    /**
     * 剧情介绍
     */
    @Column(name = "plot_desc")
    private String plotDesc;

    /**
     * 导演，数组表示，一部电影可以有多个或者至少1个，例：
     * ["1001"，"1002"]
     */
    private String directors;

    /**
     * 主要演员，数组表示，例：
     * ["", "", ""]
     */
    private String actors;

    /**
     * 剧照，数组表示，例如：
     * ["","",""]
     */
    @Column(name = "plot_pics")
    private String plotPics;

    /**
     * 创建时间，保持和电影上映时间一致
     */
    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
}