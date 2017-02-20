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
     * 商品描述
     */
    private String description;

    /**
     * 品牌ID
     */
    private String brandId;

    /**
     *品牌名称
     */
    private String brandName;

    /**
     * 类型ID
     */
    private String modelId;

    /**
     *类型名称
     */
    private String modelName;

    /**
     * 库存量
     */
    private int stock;

    /**
     * 状态
     * new -> 新品
     * discount -> 折扣品
     * inactive -> 作废
     */
    private String state;

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

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

