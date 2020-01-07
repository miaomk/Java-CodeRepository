package com.miao.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户表
 *
 * @author miao
 */
@Data
public class Users implements Serializable {

    private static final long serialVersionUID = -3946734305303957850L;
    @Id
    private String id;

    private String username;

    private String password;

    private String nickname;

    /**
     * 其实就是openId
     */
    @Column(name = "mp_wx_open_id")
    private String mpWxOpenId;

    @Column(name = "app_qq_open_id")
    private String appQqOpenId;

    @Column(name = "app_wx_open_id")
    private String appWxOpenId;

    @Column(name = "app_weibo_uid")
    private String appWeiboUid;

    /**
     * 性别 0：未知、1：男、2：女
     */
    private Integer sex;

    /**
     * 生日
     */
    private String birthday;

    /**
     * 头像
     */
    @Column(name = "face_image")
    private String faceImage;

    /**
     * 是否实名认证
     * 1：已实名
     * 0：未实名
     * 默认 0
     */
    @Column(name = "is_certified")
    private Integer isCertified;

    /**
     * 逻辑删除 0 未删除 1 已删除
     */
    @Column(name = "del_flag")
    private Boolean delFlag;

    /**
     * 创建时间
     */
    @Column(name = "regist_time")
    private LocalDateTime registTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private LocalDateTime updateTime;
}