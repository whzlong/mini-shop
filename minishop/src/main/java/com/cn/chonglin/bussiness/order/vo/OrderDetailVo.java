package com.cn.chonglin.bussiness.order.vo;

import java.math.BigDecimal;

public class OrderDetailVo {
    /**
     * 订单号
     */
    private long orderId;

    /**
     * 商品ID
     */
    private String itemId;

    /**
     * 商品名
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
     * 商品状态
     */
    private String itemState;

    /**
     * 订单价（订单支付完毕后，当时商品的单价）
     */
    private BigDecimal orderPrice;

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

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getItemState() {
        return itemState;
    }

    public void setItemState(String itemState) {
        this.itemState = itemState;
    }
}
