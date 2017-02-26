package com.cn.chonglin.bussiness.base.domain;


public class Setting {
    /**
     * 系统币种
     */
    private String currency;

    /**
     * 系统发送邮件Email
     */
    private String email;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
