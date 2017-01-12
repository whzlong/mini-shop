package com.cn.chonglin.bussiness.item.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 商品
 *
 */
public class Item {
    /**
     * 商品ID
     */
    private String itemId;

    /**
     * 商品名
     */
    private String itemName;

    /**
     * 商品列表显示图片
     */
    private String smallImage;

    /**
     * 商品详情显示图片
     */
    private String bigImage;

    /**
     * 商品单价
     */
    private BigDecimal unitPrice;

    /**
     * 商品折扣价
     */
    private BigDecimal discountPrice;

    /**
     * 币种
     */
    private String currency;

    /**
     * 是否为新品（ true: 是）
     */
    private boolean isNew;

    /**
     * 是否为打折品（true: 是）
     */
    private boolean isDiscount;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 品牌ID
     */
    private String brandId;

    /**
     * 类型ID
     */
    private String modelId;

    /**
     *
     */
    private int enabled;

    /**
     * 更新日时
     */
    private Timestamp updatedAt;

    /**
     * 创建日时
     */
    private Timestamp createdAt;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
    }

    public String getBigImage() {
        return bigImage;
    }

    public void setBigImage(String bigImage) {
        this.bigImage = bigImage;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public boolean isDiscount() {
        return isDiscount;
    }

    public void setDiscount(boolean discount) {
        isDiscount = discount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}

