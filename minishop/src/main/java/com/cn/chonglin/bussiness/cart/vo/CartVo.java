package com.cn.chonglin.bussiness.cart.vo;

import java.math.BigDecimal;
import java.util.List;

public class CartVo {
    private String cartId;
    private BigDecimal totalPrice;
    private String currency;
    private List<CartItemVo> cartItemVos;

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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<CartItemVo> getCartItemVos() {
        return cartItemVos;
    }

    public void setCartItemVos(List<CartItemVo> cartItemVos) {
        this.cartItemVos = cartItemVos;
    }
}
