package com.cn.chonglin.bussiness.order.vo;

import java.math.BigDecimal;

/**
 * 订单VO
 */
public class OrderVo {
    /**
     * 订单号
     */
    private long orderId;

    /**
     * 客户名
     */
    private String userName;

    /**
     * 订单创建日时
     */
    private String orderDatetime;

    /**
     * 支付日期
     */
    private String payDate;

    /**
     * 快递地址
     */
    private String shipAddress;

    /**
     * 订单总金额
     */
    private BigDecimal totalAmount;

    /**
     * 状态
     */
    private String state;

    /**
     * 备注
     */
    private String comment;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getOrderDatetime() {
        return orderDatetime;
    }

    public void setOrderDatetime(String orderDatetime) {
        this.orderDatetime = orderDatetime;
    }
}
