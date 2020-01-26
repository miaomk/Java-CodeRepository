package com.techwells.wumei.domain;

import java.util.Date;
import java.util.Objects;

public class SpecificationsProduct {
    private Integer spId;

    private Integer productId;

    private String specification;

    private String value;

    private Boolean activated;

    private Boolean deleted;

    private Date createdDatetime;

    private Date updatedDatetime;

    public Integer getSpId() {
        return spId;
    }

    public void setSpId(Integer spId) {
        this.spId = spId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification == null ? null : specification.trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
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

    public Date getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(Date createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public Date getUpdatedDatetime() {
        return updatedDatetime;
    }

    public void setUpdatedDatetime(Date updatedDatetime) {
        this.updatedDatetime = updatedDatetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpecificationsProduct that = (SpecificationsProduct) o;
        return Objects.equals(spId, that.spId) &&
                Objects.equals(productId, that.productId) &&
                Objects.equals(specification, that.specification) &&
                Objects.equals(value, that.value) &&
                Objects.equals(activated, that.activated) &&
                Objects.equals(deleted, that.deleted) &&
                Objects.equals(createdDatetime, that.createdDatetime) &&
                Objects.equals(updatedDatetime, that.updatedDatetime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(spId, productId, specification, value, activated, deleted, createdDatetime, updatedDatetime);
    }
}