package com.cn.chonglin.bussiness.base.domain;


public class Setting {
    /**
     * 系统币种
     */
    private String currency;

    /**
     * 系统币种简称
     */
    private String currencyAbbreviation;

    /**
     * 系统发送邮件Email
     */
    private String email;

    /**
     * 店铺地址
     */
    private String shopAddress;

    /**
     * 店铺邮编
     */
    private String shopPostcode;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrencyAbbreviation() {
        return currencyAbbreviation;
    }

    public void setCurrencyAbbreviation(String currencyAbbreviation) {
        this.currencyAbbreviation = currencyAbbreviation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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
}
