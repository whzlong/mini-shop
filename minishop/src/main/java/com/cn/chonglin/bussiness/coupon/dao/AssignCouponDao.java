package com.cn.chonglin.bussiness.coupon.dao;

import com.cn.chonglin.bussiness.coupon.domain.AssignCoupon;
import com.cn.chonglin.bussiness.coupon.vo.AssignCouponVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 分配优惠券
 */
@Repository
public class AssignCouponDao {
    private final RowMapper<AssignCouponVo> assignCouponVoRowMapper = new AssignCouponVoRowMapper();

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource datasource){
        this.jdbcTemplate = new JdbcTemplate(datasource);
    }

    /**
     * 验证用户是否已经拥有同类型的优惠券
     *
     * @param userId
     * @param couponCode
     * @return
     */
    public boolean isExist(String userId, String couponCode){
        return jdbcTemplate.queryForObject("SELECT COUNT(user_id) FROM assign_coupons WHERE user_id = ? and coupon_code = ?"
                , new Object[]{userId, couponCode}, Integer.class) > 0;
    }

    public void insert(AssignCoupon assignCoupon){
        jdbcTemplate.update("INSERT INTO assign_coupons(user_id, coupon_code, quantity, state) VALUES (?,?,?,?)"
                            , assignCoupon.getUserId()
                            , assignCoupon.getCouponCode()
                            , assignCoupon.getState());
    }

    public void batchInsert(List<AssignCoupon> assignCoupons){
        jdbcTemplate.batchUpdate("INSERT INTO assign_coupons(user_id, coupon_code, quantity, state) VALUES (?,?,?,?)"
                , assignCoupons
                , assignCoupons.size()
                , (ps, t) -> {
                    ps.setString(1, t.getUserId());
                    ps.setString(2, t.getCouponCode());
                    ps.setInt(3, t.getQuantity());
                    ps.setString(4, t.getState());
                });
    }

    public void batchUpdate(List<AssignCoupon> assignCoupons){
        jdbcTemplate.batchUpdate("UPDATE assign_coupons SET quantity = quantity + ?, state = ? WHERE user_id = ? AND coupon_code = ?"
                , assignCoupons
                , assignCoupons.size()
                , (ps, t) -> {
                    ps.setInt(1, t.getQuantity());
                    ps.setString(2, t.getState());
                    ps.setString(3, t.getUserId());
                    ps.setString(4, t.getCouponCode());
                });
    }

    public List<AssignCouponVo> queryForList(String userId){
        return jdbcTemplate.query("SELECT a.coupon_code, b.coupon_name, a.quantity, a.state, b.amount " +
                "FROM assign_coupons a " +
                "INNER JOIN coupons b ON a.coupon_code = b.code WHERE user_id = ? ORDER BY b.amount", new Object[]{userId}, assignCouponVoRowMapper);
    }

    static class AssignCouponVoRowMapper implements RowMapper<AssignCouponVo>{
        @Override
        public AssignCouponVo mapRow(ResultSet rs, int rowNum) throws SQLException {
            AssignCouponVo assignCouponVo = new AssignCouponVo();

            assignCouponVo.setCouponCode(rs.getString("coupon_code"));
            assignCouponVo.setCouponName(rs.getString("coupon_name"));
            assignCouponVo.setQuantity(rs.getInt("quantity"));
            assignCouponVo.setAmount(rs.getBigDecimal("amount"));
            assignCouponVo.setState(rs.getString("state"));

            return assignCouponVo;
        }
    }
}
