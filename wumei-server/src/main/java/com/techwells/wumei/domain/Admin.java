package com.techwells.wumei.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Admin {
    private String adminId;

    private String adminName;

    private String email;

    private String mobile;

    private Integer deptId;

    private String nickName;

    private String account;

    private String password;

    private Integer roleId;

    private Integer activated;

    private Integer deleted;

    private String description;

    private Date createdDate;

    private Date updatedDate;

}