package com.cn.chonglin.bussiness.cart.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 购物车
 */
public class Cart {
    private String cartId;
    private BigDecimal totalPrice;
    private Timestamp updatedAt;
    private Timestamp createdAt;

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
