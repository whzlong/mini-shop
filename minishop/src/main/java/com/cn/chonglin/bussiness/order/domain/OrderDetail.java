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
     * 购买数量
     */
    private int quantity;

    /**
     * 订单价（订单支付完毕后，当时商品的单价）
     */
    private BigDecimal orderPrice;

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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
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
