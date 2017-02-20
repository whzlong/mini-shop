package com.cn.chonglin.bussiness.order.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 订单详细
 */
public class OrderDetail {
    /**
     * 订单号
     */
    private long orderId;

    /**
     * 商品ID
     */
    private String itemId;

    /**
     * 商品名称
     */
    private String itemName;

    /**
     * 购买数量
     */
    private int quantity;

    /**
     * 商品单价
     */
    private BigDecimal unitPrice;

    /**
     * 商品折扣价
     */
    private BigDecimal discountPrice;

    /**
     * 更新日时
     */
    private Timestamp updatedAt;

    /**
     * 创建日时
     */
    private Timestamp createdAt;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
