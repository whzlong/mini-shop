package com.cn.chonglin.bussiness.coupon.vo;

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
     * 状态
     */
    private String state;

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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
