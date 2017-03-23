package com.cn.chonglin.bussiness.checkout;

import com.cn.chonglin.bussiness.base.dao.UserDao;
import com.cn.chonglin.bussiness.base.domain.Setting;
import com.cn.chonglin.bussiness.base.domain.User;
import com.cn.chonglin.bussiness.base.service.SettingService;
import com.cn.chonglin.bussiness.cart.dao.CartDao;
import com.cn.chonglin.bussiness.cart.domain.Cart;
import com.cn.chonglin.bussiness.checkout.vo.CheckoutPageVo;
import com.cn.chonglin.bussiness.coupon.service.CouponService;
import com.cn.chonglin.bussiness.coupon.vo.AssignCouponVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

        //总金额
        Cart cart = cartDao.findByKey(user.getId());
        checkoutPageVo.setSubtotal(cart.getTotalPrice());

        //优惠券
        List<AssignCouponVo> assignCouponVos = couponService.queryAssignedCoupons(user.getId());
        //能够使用的优惠券
        List<AssignCouponVo> usefulCoupons = new ArrayList<>();

        //优惠券总金额不能大于订单金额，因此要去除掉部分优惠券
        BigDecimal couponTotalAmount = BigDecimal.ZERO;
        for(AssignCouponVo assignCouponVo : assignCouponVos){
            couponTotalAmount = couponTotalAmount.add(assignCouponVo.getAmount());
            if(BigDecimal.ZERO.compareTo(cart.getTotalPrice().subtract(couponTotalAmount)) >= 0){
                break;
            }else{
                usefulCoupons.add(assignCouponVo);
            }
        }

        checkoutPageVo.setCoupons(usefulCoupons);


        //店铺信息
        Setting setting = settingService.findSetting();
        checkoutPageVo.setShopAddress(setting.getShopAddress());
        checkoutPageVo.setShopPostcode(setting.getShopPostcode());
        checkoutPageVo.setCurrency(setting.getCurrency());

        return checkoutPageVo;
    }

}
