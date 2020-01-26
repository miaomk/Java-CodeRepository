package com.techwells.wumei.domain;

import java.util.Date;

public class Exclusive {
    private Integer exclusiveId;

    private Integer productId;

    private Double originalPrice;

    private Double currentPrice;

    private Integer minimumLevel;

    private Integer quantityRestrictions;

    private Integer provideNumber;

    private Integer status;

    private Integer count;

    private Integer sortIndex;

    private Date startTime;

    private Date endTime;

    private Boolean activated;

    private Boolean deleted;

    private Date createdDate;

    private Date updatedDate;

    public Integer getExclusiveId() {
        return exclusiveId;
    }

    public void setExclusiveId(Integer exclusiveId) {
        this.exclusiveId = exclusiveId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Integer getMinimumLevel() {
        return minimumLevel;
    }

    public void setMinimumLevel(Integer minimumLevel) {
        this.minimumLevel = minimumLevel;
    }

    public Integer getQuantityRestrictions() {
        return quantityRestrictions;
    }

    public void setQuantityRestrictions(Integer quantityRestrictions) {
        this.quantityRestrictions = quantityRestrictions;
    }

    public Integer getProvideNumber() {
        return provideNumber;
    }

    public void setProvideNumber(Integer provideNumber) {
        this.provideNumber = provideNumber;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(Integer sortIndex) {
        this.sortIndex = sortIndex;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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