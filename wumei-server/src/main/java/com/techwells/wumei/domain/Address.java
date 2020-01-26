package com.techwells.wumei.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Address {
    private Integer addressId;

    private Integer userId;

    private String addressee;

    private String addresseePhone;

    private String provinceCode;

    private String cityCode;

    private String districtCode;

    private String provinceCity;

    private String address;

    private Integer isDefault;

    private Float longitude;

    private Float latitude;

    private Boolean activated;

    private Boolean deleted;

    private Date createdDate;

    private Date updatedDate;
}