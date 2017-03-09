package com.cn.chonglin.bussiness.coupon.dao;

import com.cn.chonglin.bussiness.coupon.domain.AssignCoupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * 分配优惠券
 */
@Repository
public class AssignCouponDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource datasource){
        this.jdbcTemplate = new JdbcTemplate(datasource);
    }

    public void insert(AssignCoupon assignCoupon){
        jdbcTemplate.update("INSERT INTO assign_coupons(user_id, coupon_code, state) VALUES (?,?,?)"
                            , assignCoupon.getUserId()
                            , assignCoupon.getCouponCode()
                            , assignCoupon.getState());
    }


}
