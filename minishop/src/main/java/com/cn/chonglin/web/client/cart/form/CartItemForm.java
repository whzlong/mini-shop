package com.cn.chonglin.web.client.cart.form;

import com.cn.chonglin.bussiness.cart.domain.CartItem;

/**
 * 购物车商品
 */
public class CartItemForm {
    private String itemId;
    private int quantity;
    private String color;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public CartItem toDomain(){
        CartItem cartItem = new CartItem();

        cartItem.setItemId(this.itemId);
        cartItem.setQuantity(this.quantity);
        cartItem.setColor(this.color);

        return cartItem;
    }
}
