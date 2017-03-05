package com.cn.chonglin.bussiness.order.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

/**
 * 订单
 */
public class Order {
    /**
     * 订单号
     */
    private long orderId;

    /**
     * 客户ID
     */
    private String userId;

    /**
     * 支付日期
     */
    private LocalDate payDate;

    /**
     * 寄送地址
     */
    private String shipAddress;

    /**
     * 订单总金额
     */
    private BigDecimal totalAmount;

    /**
     * 备注
     */
    private String comment;

    /**
     * 订单状态（appointment:预约; unpaid:未支付; paid:已支付；repairing：维修中；over:已完成）
     */
    private String state;

    /**
     * 优惠券代码
     */
    private String couponCode;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDate getPayDate() {
        return payDate;
    }

    public void setPayDate(LocalDate payDate) {
        this.payDate = payDate;
    }

    public String getShipAddress() {
        return shipAddress;
    }

    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
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
