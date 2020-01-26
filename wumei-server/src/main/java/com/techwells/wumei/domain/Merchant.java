package com.techwells.wumei.domain;

import lombok.Data;

import java.util.Date;
@Data
public class Merchant {
    private Integer userId;

    private String merchantName;

    private Integer merchantType;

    private String merchantIcon;

    private String merchantLogo;

    private String enterpriseName;

    private String invoiceTitle;

    private String majorBusiness;

    private String manager;

    private Integer age;

    private String address;

    private String phone;

    private String mobile;

    private String publicDepositBank;

    private String publicAccount;

    private String publicCertificate;

    private String businessLicence;

    private String jurisdicalPerson;

    private String jurisdicalMobile;

    private String identificationCard;

    private String identificationBehind;

    private String identificationHold;

    private String identificationFront;

    private String agentName;

    private String agentMobile;

    private String agentIdcard;

    private String agentIdcardBehind;

    private String agentIdcardHold;

    private String agentIdcardFront;

    private Integer integral;

    private String merchantQrcode;

    private String inviteQrcode;

    private Double latitude;

    private Double longitude;

    private String description;

    private String failureReason;

    private String brandName;

    private String brandImage;

    private Integer sendMode;

    private String remark;

    private Integer reviewerId;

    private Integer isRecommend;

    private Integer activated;

    private Boolean deleted;

    private Date createdDate;

    private Date updatedDate;

}