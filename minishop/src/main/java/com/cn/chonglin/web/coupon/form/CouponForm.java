package com.cn.chonglin.web.coupon.form;

import com.cn.chonglin.bussiness.coupon.domain.Coupon;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigDecimal;
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
     * 优惠券金额
     */
    @NotEmpty(message = "Please input the amount")
    private String amount;

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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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

        if(!StringUtils.isEmpty(this.amount)){
            coupon.setAmount(new BigDecimal(this.amount));
        }

        return coupon;
    }
}
