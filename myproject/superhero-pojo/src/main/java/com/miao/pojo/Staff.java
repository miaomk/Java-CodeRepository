package com.miao.pojo;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * 演职人员表，主要包含 导演 演员 等
 *
 * @author miao
 */
@Data
public class Staff implements Serializable {

    private static final long serialVersionUID = -3946734305303957850L;
    @Id
    private String id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别：
     * 1： 男
     * 0： 女
     */
    private Integer sex;

    /**
     * 人物照片
     */
    private String photo;
}