package com.cn.chonglin.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Braintree访问配置
 */
@ConfigurationProperties(prefix="minishop.braintree")
public class BraintreeProperties {
    private String enviroment;
    private String merchantId;
    private String publicKey;
    private String privateKey;

    public String getEnviroment() {

        return enviroment;
    }

    public void setEnviroment(String enviroment) {
        this.enviroment = enviroment;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}
