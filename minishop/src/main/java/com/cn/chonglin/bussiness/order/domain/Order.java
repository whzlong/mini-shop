package com.cn.chonglin.bussiness.order.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

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
    private String customerId;

    /**
     * 客户名称
     */
    private String customerName;

    /**
     * 订单日期（预约日期）
     */
    private String orderDate;

    /**
     * 订单时间（预约时间）
     */
    private String orderTime;

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
     * 订单状态（appointment:预约; created:创建; paid:已支付；repairing：维修中；over:已完成）
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

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
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
