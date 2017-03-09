package com.cn.chonglin.web.payment;

/**
 * 客户端向Braintree服务器端发送请求时携带的token.
 */
public class ClientTokenVo {
    private String clientToken;

    public String getClientToken() {
        return clientToken;
    }

    public void setClientToken(String clientToken) {
        this.clientToken = clientToken;
    }
}
