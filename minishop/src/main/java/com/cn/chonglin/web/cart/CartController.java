package com.cn.chonglin.web.cart;

import com.cn.chonglin.bussiness.cart.service.CartService;
import com.cn.chonglin.bussiness.cart.vo.CartVo;
import com.cn.chonglin.common.ResponseResult;
import com.cn.chonglin.web.cart.form.CartItemForm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 购物车
 *
 */
@RestController
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("client/cart-items")
    public ResponseResult<CartVo> getCart(){
        CartVo cartVo = cartService.getCart();

        return ResponseResult.success(cartVo);
    }

    @PostMapping(value = "client/cart-items", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<Object> addCartItem(@Valid CartItemForm cartItemForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseResult.error(bindingResult.getFieldErrors());
        }

        cartService.addCartItem(cartItemForm.toDomain());

        return ResponseResult.success(null);
    }

    @PostMapping(value = "client/cart-items/{itemId}/delete", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<CartVo> deleteCartItem(@PathVariable String itemId){

        CartVo cartVo = cartService.deleteCartItem(itemId);

        return ResponseResult.success(cartVo);
    }

    @PostMapping(value = "client/cart-items/update", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<CartVo> updateCart(@RequestBody List<CartItemForm> cartItemForms){
        for(CartItemForm item : cartItemForms){
            if(!StringUtils.isNumeric(item.getQuantity())){
                return ResponseResult.error(1, "The quantity must be integer.");
            }
        }

        CartVo cartVo = cartService.updateCartItems(cartItemForms);

        return ResponseResult.success(cartVo);
    }
}
