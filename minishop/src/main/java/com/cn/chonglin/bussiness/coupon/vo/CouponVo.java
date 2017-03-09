package com.cn.chonglin.bussiness.coupon.vo;

import java.math.BigDecimal;

public class CouponVo {
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
    private String validDateFrom;

    /**
     * 有效日期（结束）
     */
    private String validDateTo;

    /**
     * 优惠金额
     */
    private BigDecimal amount;

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

    public String getValidDateFrom() {
        return validDateFrom;
    }

    public void setValidDateFrom(String validDateFrom) {
        this.validDateFrom = validDateFrom;
    }

    public String getValidDateTo() {
        return validDateTo;
    }

    public void setValidDateTo(String validDateTo) {
        this.validDateTo = validDateTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
