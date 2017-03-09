package com.cn.chonglin.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Paypal访问配置
 */
@ConfigurationProperties(prefix="minishop.paypal")
public class PaypalProperties {
    private String account;
    private String accesstoken;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }
}
