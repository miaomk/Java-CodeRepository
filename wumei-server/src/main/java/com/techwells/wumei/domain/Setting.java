package com.techwells.wumei.domain;

import java.util.Date;

public class Setting {
    private Integer settingId;

    private Integer winningProbability;

    private Integer consumptionIntegral;

    private Date drawStarttime;

    private Date drawEndtime;

    private String awardRule;

    private String contactDownloadUrl;

    private String qrcodeBgUrl;

    private Double usingFee;

    private Double managementFee;

    private Double salesIncome;

    private Double assistantIncome;

    private Double wechatFee;

    private Double integralProportion;

    private Double serviceInvoice;

    private Double freightInvoice;

    private Integer creatorId;

    private Boolean activated;

    private Boolean deleted;

    private Date createdDate;

    private Date updatedDate;

    public Integer getSettingId() {
        return settingId;
    }

    public void setSettingId(Integer settingId) {
        this.settingId = settingId;
    }

    public Integer getWinningProbability() {
        return winningProbability;
    }

    public void setWinningProbability(Integer winningProbability) {
        this.winningProbability = winningProbability;
    }

    public Integer getConsumptionIntegral() {
        return consumptionIntegral;
    }

    public void setConsumptionIntegral(Integer consumptionIntegral) {
        this.consumptionIntegral = consumptionIntegral;
    }

    public Date getDrawStarttime() {
        return drawStarttime;
    }

    public void setDrawStarttime(Date drawStarttime) {
        this.drawStarttime = drawStarttime;
    }

    public Date getDrawEndtime() {
        return drawEndtime;
    }

    public void setDrawEndtime(Date drawEndtime) {
        this.drawEndtime = drawEndtime;
    }

    public String getAwardRule() {
        return awardRule;
    }

    public void setAwardRule(String awardRule) {
        this.awardRule = awardRule == null ? null : awardRule.trim();
    }

    public String getContactDownloadUrl() {
        return contactDownloadUrl;
    }

    public void setContactDownloadUrl(String contactDownloadUrl) {
        this.contactDownloadUrl = contactDownloadUrl == null ? null : contactDownloadUrl.trim();
    }

    public String getQrcodeBgUrl() {
        return qrcodeBgUrl;
    }

    public void setQrcodeBgUrl(String qrcodeBgUrl) {
        this.qrcodeBgUrl = qrcodeBgUrl == null ? null : qrcodeBgUrl.trim();
    }

    public Double getUsingFee() {
        return usingFee;
    }

    public void setUsingFee(Double usingFee) {
        this.usingFee = usingFee;
    }

    public Double getManagementFee() {
        return managementFee;
    }

    public void setManagementFee(Double managementFee) {
        this.managementFee = managementFee;
    }

    public Double getSalesIncome() {
        return salesIncome;
    }

    public void setSalesIncome(Double salesIncome) {
        this.salesIncome = salesIncome;
    }

    public Double getAssistantIncome() {
        return assistantIncome;
    }

    public void setAssistantIncome(Double assistantIncome) {
        this.assistantIncome = assistantIncome;
    }

    public Double getWechatFee() {
        return wechatFee;
    }

    public void setWechatFee(Double wechatFee) {
        this.wechatFee = wechatFee;
    }

    public Double getIntegralProportion() {
        return integralProportion;
    }

    public void setIntegralProportion(Double integralProportion) {
        this.integralProportion = integralProportion;
    }

    public Double getServiceInvoice() {
        return serviceInvoice;
    }

    public void setServiceInvoice(Double serviceInvoice) {
        this.serviceInvoice = serviceInvoice;
    }

    public Double getFreightInvoice() {
        return freightInvoice;
    }

    public void setFreightInvoice(Double freightInvoice) {
        this.freightInvoice = freightInvoice;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}