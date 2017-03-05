package com.cn.chonglin.web.coupon.form;

import com.cn.chonglin.bussiness.coupon.domain.Coupon;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 优惠券表单
 *
 */
public class CouponForm {
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
    @NotEmpty(message = "Please input the valid date(from)")
    private String validDateFrom;

    /**
     * 有效日期（结束）
     */
    @NotEmpty(message = "Please input the valid date(to)")
    private String validDateTo;

    /**
     * 状态
     */
    @NotEmpty(message = "Please input the state")
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

    public Coupon toDomain(){
        Coupon coupon = new Coupon();

        coupon.setCode(this.code);
        coupon.setCouponName(this.couponName);
        if(!StringUtils.isEmpty(this.validDateFrom)){
            coupon.setValidDateFrom(LocalDate.parse(this.validDateFrom, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        }

        if(!StringUtils.isEmpty(this.validDateTo)){
            coupon.setValidDateTo(LocalDate.parse(this.validDateTo, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        }
        coupon.setState(this.state);

        return coupon;
    }
}
