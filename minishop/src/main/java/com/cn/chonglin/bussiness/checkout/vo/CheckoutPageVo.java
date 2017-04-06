package com.cn.chonglin.bussiness.checkout.vo;

import com.cn.chonglin.bussiness.coupon.vo.AssignCouponVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * Checkout
 *
 */
public class CheckoutPageVo {
    private String shopAddress;
    private String shopPostcode;
    private String shopTelephone;
    private String currency;
    private List<AssignCouponVo> coupons;
    private BigDecimal subtotal;

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopPostcode() {
        return shopPostcode;
    }

    public void setShopPostcode(String shopPostcode) {
        this.shopPostcode = shopPostcode;
    }

    public String getShopTelephone() {
        return shopTelephone;
    }

    public void setShopTelephone(String shopTelephone) {
        this.shopTelephone = shopTelephone;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<AssignCouponVo> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<AssignCouponVo> coupons) {
        this.coupons = coupons;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}
