package com.techwells.wumei.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
/**
 * @author miao
 */
@Data
public class WithdrawalRecord {
    private Integer recordId;

    private Integer userId;

    private Double withdrawalAmount;

    private String realName;

    private String withdrawalAccount;

    private String bankName;

    private Integer withdrawalType;

    private Integer activated;

    private Boolean deleted;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime createDate;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime updateDate;

}