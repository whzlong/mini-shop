package com.cn.chonglin.bussiness.coupon.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 优惠券
 *
 */
public class Coupon {
    /**
     * 优惠券代码
     */
    private String code;

    /**
     * 优惠券名称
     */
    private String couponName;

    /**
     * 有效日期（开始）
     */
    private LocalDate validDateFrom;

    /**
     * 有效日期（结束）
     */
    private LocalDate validDateTo;

    /**
     * 优惠金额
     */
    private BigDecimal amount;

    /**
     * 更新日时
     */
    private LocalDateTime updatedAt;

    /**
     * 创建日时
     */
    private LocalDateTime createdAt;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public LocalDate getValidDateFrom() {
        return validDateFrom;
    }

    public void setValidDateFrom(LocalDate validDateFrom) {
        this.validDateFrom = validDateFrom;
    }

    public LocalDate getValidDateTo() {
        return validDateTo;
    }

    public void setValidDateTo(LocalDate validDateTo) {
        this.validDateTo = validDateTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
