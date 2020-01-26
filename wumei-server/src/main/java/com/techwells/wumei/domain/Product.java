package com.techwells.wumei.domain;

import java.util.Date;

/**
 * 商品实体类
 *
 * @author miao
 */
public class Product {
    private Integer productId;

    private Integer merchantId;

    private String productName;

    private String productNameEn;

    private String productIcon;

    private String unit;

    private Integer productType;

    private Double salePrice;

    private Double purchasePrice;

    private Boolean isScoreProduct;

    private Double scoreProportion;

    private String specification;

    private Integer stock;

    private Integer brandId;

    private String productionPlace;

    private String productProfile;

    private String material;

    private Double express;

    private Integer activated;

    private Boolean deleted;

    private Date createdDate;

    private Date updatedDate;

    private String description;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getProductNameEn() {
        return productNameEn;
    }

    public void setProductNameEn(String productNameEn) {
        this.productNameEn = productNameEn == null ? null : productNameEn.trim();
    }

    public String getProductIcon() {
        return productIcon;
    }

    public void setProductIcon(String productIcon) {
        this.productIcon = productIcon == null ? null : productIcon.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public Double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Boolean getIsScoreProduct() {
        return isScoreProduct;
    }

    public void setIsScoreProduct(Boolean isScoreProduct) {
        this.isScoreProduct = isScoreProduct;
    }

    public Double getScoreProportion() {
        return scoreProportion;
    }

    public void setScoreProportion(Double scoreProportion) {
        this.scoreProportion = scoreProportion;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification == null ? null : specification.trim();
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getProductionPlace() {
        return productionPlace;
    }

    public void setProductionPlace(String productionPlace) {
        this.productionPlace = productionPlace == null ? null : productionPlace.trim();
    }

    public String getProductProfile() {
        return productProfile;
    }

    public void setProductProfile(String productProfile) {
        this.productProfile = productProfile == null ? null : productProfile.trim();
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material == null ? null : material.trim();
    }

    public Double getExpress() {
        return express;
    }

    public void setExpress(Double express) {
        this.express = express;
    }

    public Integer getActivated() {
        return activated;
    }

    public void setActivated(Integer activated) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}
