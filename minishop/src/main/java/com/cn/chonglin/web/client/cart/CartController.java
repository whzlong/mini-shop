package com.cn.chonglin.web.client.cart;

import com.cn.chonglin.bussiness.cart.domain.CartItem;
import com.cn.chonglin.bussiness.cart.service.CartService;
import com.cn.chonglin.web.client.cart.form.CartItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String index(@PathVariable String id, ModelMap modelMap){

        List<CartItem> cartItems = cartService.getCartItems(id);

        modelMap.addAttribute("cartItems", cartItems);

        return "client/cart";
    }

    @PostMapping("cart-item")
    public String add(@Valid CartItemForm cartItemForm, ModelMap modelMap){

        cartService.addCartItem(cartItemForm.toDomain());

        return "redirect: /client/cart";
    }
}
