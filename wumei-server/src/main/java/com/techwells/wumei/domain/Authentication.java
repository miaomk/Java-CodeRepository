package com.techwells.wumei.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 实名认证实体类
 *
 * @author miao
 */
@Data
public class Authentication {
    private Integer authenticationId;

    private Integer relationId;

    private String realName;

    private String idCard;

    private String bankName;

    private String bankNumber;

    private String mobile;

    private Integer authenticationType;

    private Integer creatorId;

    private Integer activated;

    private Byte deleted;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateDate;

}