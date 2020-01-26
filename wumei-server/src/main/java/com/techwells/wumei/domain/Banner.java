package com.techwells.wumei.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 轮播图实体类
 *
 * @author Administrator
 */
@Data
public class Banner {
    private Integer bannerId;

    private Integer bannerType;

    private String bannerName;

    private String imageUrl;

    private Date startDate;

    private Date endDate;

    private Integer sortIndex;

    private String jumpUrl;

    private String description;

    private Integer activated;

    private Integer deleted;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createdDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updatedDate;

}