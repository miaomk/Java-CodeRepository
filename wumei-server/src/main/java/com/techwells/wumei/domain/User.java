package com.techwells.wumei.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class User implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -600110954386923741L;

	private Integer userId;

    private String snsId;
    @JsonIgnore
    private String mpId;

    private String userName;

    @JsonIgnore
    private String password;

    private String nickName;

    private Integer userType;

    private String hxUsername;

    private String hxPassword;

    private String userIcon;

    private String realName;

    private Integer level;

    private String hobby;

    private Integer score;

    private Integer accumulatedScore;

    private Integer invitedCode;

    private String signature;

    private String gender;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date birthday;

    private Integer age;

    private String mobile;

    private String email;

    private String address;

    private Double latitude;

    private Double longitude;

    private String cityCode;

    private String profession;

    private String company;

    private Integer sourceId;

    private Integer recommandId;

    private Integer companyId;

    private String position;

    private String personalProfile;

    private Integer isAuthentication;

    private Boolean activated;

    private Boolean deleted;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updatedDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createdDate;

}