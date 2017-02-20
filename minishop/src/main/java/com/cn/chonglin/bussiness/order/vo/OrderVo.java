package com.cn.chonglin.bussiness.order.vo;

import java.math.BigDecimal;

/**
 * 订单VO
 */
public class OrderVo {
    /**
     * 行号
     */
    private int rowNum;

    /**
     * 订单号
     */
    private long orderId;

    /**
     * 客户名
     */
    private String customerName;

    /**
     * 下单日时（预约日时）
     */
    private String datetime;

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

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
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
}
