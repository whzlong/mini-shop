package com.cn.chonglin.bussiness.cart.service;

import com.cn.chonglin.bussiness.base.dao.UserDao;
import com.cn.chonglin.bussiness.base.domain.User;
import com.cn.chonglin.bussiness.cart.dao.CartDao;
import com.cn.chonglin.bussiness.cart.dao.CartItemDao;
import com.cn.chonglin.bussiness.cart.domain.Cart;
import com.cn.chonglin.bussiness.cart.domain.CartItem;
import com.cn.chonglin.bussiness.item.dao.ItemDao;
import com.cn.chonglin.bussiness.item.domain.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 购物车Service
 */
@Service
public class CartService {
    @Autowired
    private CartDao cartDao;

    @Autowired
    private CartItemDao cartItemDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ItemDao itemDao;

    /**
     * 向购物车添加商品
     *
     * @param cartItem
     */
    public void addCartItem(CartItem cartItem){

        Cart cart = getCartInfo();

        cartItem.setCartId(cart.getCartId());

        if(cartItemDao.findByKey(cart.getCartId(), cartItem.getItemId()) == null){
            Item item = itemDao.findByKey(cartItem.getItemId());

            cartItem.setItemName(item.getItemName());

            cartItemDao.insert(cartItem);
        }else{
            cartItemDao.update(cartItem);
        }

    }

    public void updateCart(Cart cart){

    }

    /**
     * 用户ID作为购物车ID
     *
     * @return
     */
    private Cart getCartInfo(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userDao.findByEmail(username);

        Cart cart = cartDao.findByKey(user.getId());

        if(cart == null){
            cart = new Cart();
            cart.setCartId(user.getId());
            cart.setTotalPrice(BigDecimal.ZERO);

            cartDao.insert(cart);
        }

        return cart;
    }

    public List<CartItem> getCartItems(String cartId){
        return cartItemDao.findByCartId(cartId);
    }


}
