package com.cn.chonglin.bussiness.coupon.service;

import com.cn.chonglin.bussiness.coupon.dao.AssignCouponDao;
import com.cn.chonglin.bussiness.coupon.dao.CouponDao;
import com.cn.chonglin.bussiness.coupon.domain.AssignCoupon;
import com.cn.chonglin.bussiness.coupon.domain.Coupon;
import com.cn.chonglin.bussiness.coupon.vo.AssignCouponVo;
import com.cn.chonglin.bussiness.coupon.vo.CouponVo;
import com.cn.chonglin.common.IdGenerator;
import com.cn.chonglin.common.ListPage;
import com.cn.chonglin.constants.DropdownListContants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 优惠券Service
 */
@Service
@Transactional(readOnly = true)
public class CouponService {
    @Autowired
    private CouponDao couponDao;

    @Autowired
    private AssignCouponDao assignCouponDao;

    public ListPage<CouponVo> query(String code, String couponName, int limit, int offset){
        int count = couponDao.count(code, couponName);

        List<CouponVo> listItems = couponDao.query(code, couponName, limit, offset*limit);

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
        currentCoupon.setAmount(coupon.getAmount());

        couponDao.update(currentCoupon);

    }

    /**
     * 给用户分配优惠券
     *
     * @param userIds
     *          目标用户
     * @param coupons
     *          优惠券
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void assignCoupons(String[] userIds, String[] coupons){
        //新分配的优惠券
        List<AssignCoupon> insertAssignCoupons = new ArrayList<>();
        //现存的优惠券
        List<AssignCoupon> updateAssignCoupons = new ArrayList<>();

        AssignCoupon assignCoupon = null;

        for (String userId : userIds){

            for (String couponCode : coupons){
                assignCoupon = new AssignCoupon();

                assignCoupon.setUserId(userId);
                assignCoupon.setCouponCode(couponCode);
                assignCoupon.setQuantity(1);
                assignCoupon.setState(DropdownListContants.COUPON_STATE_ACTIVE);

                if(assignCouponDao.isExist(userId, couponCode)){
                    updateAssignCoupons.add(assignCoupon);
                }else{
                    insertAssignCoupons.add(assignCoupon);
                }

            }
        }

        assignCouponDao.batchInsert(insertAssignCoupons);

        assignCouponDao.batchUpdate(updateAssignCoupons);

    }

    /**
     * 查询分配给指定用户的优惠券
     *
     * @param userId
     */
    public List<AssignCouponVo> queryAssignedCoupons(String userId){
        return assignCouponDao.queryForList(userId);
    }
}
