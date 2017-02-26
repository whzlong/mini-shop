package com.cn.chonglin.web.client.cart;

import com.cn.chonglin.bussiness.cart.service.CartService;
import com.cn.chonglin.bussiness.cart.vo.CartVo;
import com.cn.chonglin.common.ResponseResult;
import com.cn.chonglin.web.client.cart.form.CartItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 购物车控制器
 *
 */
@Controller
@RequestMapping("client")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("cart")
    public String index(){
        return "client/cart";
    }

    @GetMapping("cart-items")
    public @ResponseBody
    ResponseResult<CartVo> getCart(){
        CartVo cartVo = cartService.getCart();

        return ResponseResult.success(cartVo);
    }

    @GetMapping("cart-item")
    public @ResponseBody ResponseResult<Object> addCartItem(@Valid CartItemForm cartItemForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseResult.error(bindingResult.getFieldErrors());
        }

        cartService.addCartItem(cartItemForm.toDomain());

        return ResponseResult.success(null);
    }

    @PostMapping("cart-items/{itemId}/delete")
    public @ResponseBody ResponseResult<CartVo> deleteCartItem(@PathVariable String itemId){

        CartVo cartVo = cartService.deleteCartItem(itemId);

        return ResponseResult.success(cartVo);
    }

    @PostMapping("cart-items/update")
    public @ResponseBody ResponseResult<CartVo> updateCart(@RequestBody List<CartItemForm> cartItemForms){
        CartVo cartVo = cartService.updateCartItems(cartItemForms);

        return ResponseResult.success(cartVo);
    }
}
