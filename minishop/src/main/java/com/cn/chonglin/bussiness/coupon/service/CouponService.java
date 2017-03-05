package com.cn.chonglin.bussiness.coupon.service;

import com.cn.chonglin.bussiness.coupon.dao.CouponDao;
import com.cn.chonglin.bussiness.coupon.domain.Coupon;
import com.cn.chonglin.bussiness.coupon.vo.CouponVo;
import com.cn.chonglin.common.IdGenerator;
import com.cn.chonglin.common.ListPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 优惠券Service
 */
@Service
@Transactional(readOnly = true)
public class CouponService {
    @Autowired
    private CouponDao couponDao;

    public ListPage<CouponVo> query(String code, String couponName, String state, int limit, int offset){
        int count = couponDao.count(code, couponName, state);

        List<CouponVo> listItems = couponDao.query(code, couponName, state, limit, offset*limit);

        return new ListPage<>(count, listItems);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CouponVo save(Coupon coupon){
        String code = coupon.getCode();

        if(StringUtils.isEmpty(code)){
            code = add(coupon);
        }else{
            update(coupon);
        }

        return couponDao.findVoByKey(code);
    }

    private String add(Coupon coupon){
        String code;

        while (true){
            code = IdGenerator.createCouponCode();

            if(!couponDao.hasCouponCode(code)){
                coupon.setCode(code);
                break;
            }
        }

        couponDao.insert(coupon);

        return code;
    }

    private void update(Coupon coupon){
        Coupon currentCoupon = couponDao.findByKey(coupon.getCode());

        currentCoupon.setCouponName(coupon.getCouponName());
        currentCoupon.setValidDateFrom(coupon.getValidDateFrom());
        currentCoupon.setValidDateTo(coupon.getValidDateTo());
        currentCoupon.setState(coupon.getState());

        couponDao.update(currentCoupon);

    }
}
