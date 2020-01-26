package com.techwells.wumei.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
@Data
public class Evaluation {
    private Integer evaluationId;

    private Integer userId;

    private Integer orderId;

    private Integer productScore;

    private Integer expressScore;

    private Integer serviceScore;

    private Integer evaluationLevel;

    private String content;

    private Boolean activated;

    private Boolean deleted;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updatedDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createdDate;

    private String imageUrl;
}