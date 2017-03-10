package com.cn.chonglin.bussiness.checkout;

import com.cn.chonglin.bussiness.base.dao.UserDao;
import com.cn.chonglin.bussiness.base.domain.Setting;
import com.cn.chonglin.bussiness.base.domain.User;
import com.cn.chonglin.bussiness.base.service.SettingService;
import com.cn.chonglin.bussiness.cart.dao.CartDao;
import com.cn.chonglin.bussiness.cart.domain.Cart;
import com.cn.chonglin.bussiness.checkout.vo.CheckoutPageVo;
import com.cn.chonglin.bussiness.coupon.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Checkout Service
 *
 */
@Service
public class CheckoutService {
    @Autowired
    private SettingService settingService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CartDao cartDao;

    public CheckoutPageVo getInitData(){
        CheckoutPageVo checkoutPageVo = new CheckoutPageVo();

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userDao.findByEmail(username);

        //优惠券
        checkoutPageVo.setCoupons(couponService.queryAssignedCoupons(user.getId()));

        //总金额
        Cart cart = cartDao.findByKey(user.getId());
        checkoutPageVo.setSubtotal(cart.getTotalPrice());

        //店铺信息
        Setting setting = settingService.findSetting();
        checkoutPageVo.setShopAddress(setting.getShopAddress());
        checkoutPageVo.setShopPostcode(setting.getShopPostcode());
        checkoutPageVo.setCurrency(setting.getCurrency());

        return checkoutPageVo;
    }

}
