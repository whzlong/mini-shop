package com.cn.chonglin.web.client.cart.form;

import com.cn.chonglin.bussiness.cart.domain.CartItem;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

/**
 * 购物车商品
 */
public class CartItemForm {
    private String itemId;
    @NotEmpty(message = "Please input the quantity.")
    @Pattern(regexp = "^[0-9]*[1-9][0-9]*$", message = "Please input the integer quantity")
    private String quantity;
    private String color;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
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
        cartItem.setQuantity(Integer.valueOf(this.quantity));
        cartItem.setColor(this.color);

        return cartItem;
    }
}
