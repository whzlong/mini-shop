package com.cn.chonglin.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Paypal访问配置
 */
@ConfigurationProperties(prefix="minishop.paypal")
public class PaypalProperties {
    private String account;
    private String accesstoken;
    private String clientId;
    private String secret;

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

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
