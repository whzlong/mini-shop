package com.cn.chonglin.bussiness.cart.service;

import com.cn.chonglin.bussiness.base.dao.SettingDao;
import com.cn.chonglin.bussiness.base.dao.UserDao;
import com.cn.chonglin.bussiness.base.domain.Setting;
import com.cn.chonglin.bussiness.base.domain.User;
import com.cn.chonglin.bussiness.cart.dao.CartDao;
import com.cn.chonglin.bussiness.cart.dao.CartItemDao;
import com.cn.chonglin.bussiness.cart.domain.Cart;
import com.cn.chonglin.bussiness.cart.domain.CartItem;
import com.cn.chonglin.bussiness.cart.vo.CartItemVo;
import com.cn.chonglin.bussiness.cart.vo.CartVo;
import com.cn.chonglin.bussiness.item.dao.ItemDao;
import com.cn.chonglin.bussiness.item.domain.Item;
import com.cn.chonglin.web.cart.form.CartItemForm;
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

    @Autowired
    private SettingDao settingDao;

    /**
     * 向购物车添加商品
     *
     * @param cartItem
     */
    public void addCartItem(CartItem cartItem){

        Cart cart = getCartInfo();

        cartItem.setCartId(cart.getCartId());

        CartItem currentCartItem = cartItemDao.findByKey(cart.getCartId(), cartItem.getItemId());

        if(currentCartItem == null){
            cartItemDao.insert(cartItem);
        }else{
            currentCartItem.setQuantity(currentCartItem.getQuantity() + cartItem.getQuantity());

            cartItemDao.update(currentCartItem);
        }

        Item item = itemDao.findByKey(cartItem.getItemId());

        if("discount".equals(item.getState())){
            cart.setTotalPrice(cart.getTotalPrice().add(item.getDiscountPrice().multiply(new BigDecimal(cartItem.getQuantity()))));
        }else{
            cart.setTotalPrice(cart.getTotalPrice().add(item.getUnitPrice().multiply(new BigDecimal(cartItem.getQuantity()))));
        }

        cartDao.update(cart);

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

    public CartVo getCart(){
        Cart cart = getCartInfo();

        CartVo cartVo = new CartVo();

        cartVo.setCartId(cart.getCartId());
        cartVo.setTotalPrice(cart.getTotalPrice());
        cartVo.setCartItemVos(cartItemDao.findCartItems(cart.getCartId()));

        Setting setting = settingDao.queryForObject();

        cartVo.setCurrency(setting.getCurrency());

        return cartVo;
    }

    public CartVo deleteCartItem(String itemId){
        Cart cart = getCartInfo();

        cartItemDao.delete(cart.getCartId(), itemId);

        return updateCart(cart);
    }

    /**
     * 清空购物车
     *
     * @param cartId
     */
    public void clearCart(String cartId){
        cartItemDao.deleteAll(cartId);

        Cart cart = cartDao.findByKey(cartId);
        cart.setTotalPrice(BigDecimal.ZERO);

        cartDao.update(cart);
    }

    public CartVo updateCartItems(List<CartItemForm> cartItemForms){
        Cart cart = getCartInfo();

        CartItem cartItem = null;

        for (CartItemForm form : cartItemForms){
            cartItem = cartItemDao.findByKey(cart.getCartId(), form.getItemId());
            cartItem.setQuantity(Integer.valueOf(form.getQuantity()));

            cartItemDao.update(cartItem);
        }

        return updateCart(cart);
    }

    private CartVo updateCart(Cart cart){
        List<CartItemVo> cartItemVos = cartItemDao.findCartItems(cart.getCartId());

        BigDecimal totalPrice = BigDecimal.ZERO;

        for(CartItemVo cartItemVo : cartItemVos){
            if ("discount".equals(cartItemVo.getState())){
                totalPrice = totalPrice.add(cartItemVo.getDiscountPrice().multiply(new BigDecimal(cartItemVo.getQuantity())));
            }else {
                totalPrice = totalPrice.add(cartItemVo.getUnitPrice().multiply(new BigDecimal(cartItemVo.getQuantity())));
            }
        }

        cart.setTotalPrice(totalPrice);

        cartDao.update(cart);

        CartVo cartVo = new CartVo();

        cartVo.setCartId(cart.getCartId());
        cartVo.setTotalPrice(cart.getTotalPrice());
        cartVo.setCartItemVos(cartItemVos);

        return cartVo;

    }

}
