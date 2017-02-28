package com.cn.chonglin.bussiness.base.domain;

import java.time.LocalDateTime;

/**
 * 注册验证
 *
 */
public class Verification {
    private String verificationCode;

    private String userId;

    private LocalDateTime created_at;

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }
}
