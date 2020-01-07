package com.miao.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 演职人员和电影的多对的关联关系表
 *
 * @author miao
 */
@Data
@Table(name = "staff_movie")
public class StaffMovie implements Serializable {

    private static final long serialVersionUID = -3946734305303957850L;
    @Id
    private String id;

    /**
     * 演职人员id
     */
    @Column(name = "staff_id")
    private String staffId;

    /**
     * 电影id
     */
    @Column(name = "movie_id")
    private String movieId;

    /**
     * 角色：
     * 1：导演
     * 2：演员
     */
    private Integer role;

    /**
     * 饰演人物
     */
    @Column(name = "act_name")
    private String actName;
}