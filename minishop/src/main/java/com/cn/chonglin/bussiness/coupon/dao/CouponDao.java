package com.cn.chonglin.bussiness.coupon.dao;

import com.cn.chonglin.bussiness.coupon.domain.Coupon;
import com.cn.chonglin.bussiness.coupon.vo.CouponVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 优惠券Dao
 */
@Repository
public class CouponDao {
    private final RowMapper<CouponVo> couponVoRowMapper = new CouponVoRowMapper();
    private final RowMapper<Coupon> couponRowMapper = new CouponRowMapper();

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Coupon findByKey(String code){
        return jdbcTemplate.queryForObject("SELECT * FROM coupons WHERE code = ?", new Object[]{code}, couponRowMapper);
    }

    public CouponVo findVoByKey(String code){
        return jdbcTemplate.queryForObject("SELECT * FROM coupons WHERE code = ?", new Object[]{code}, couponVoRowMapper);
    }

    /**
     * 检查优惠券代码是否存在
     *
     * @param code
     * @return
     */
    public boolean hasCouponCode(String code){
        return jdbcTemplate.queryForObject("SELECT count(code) FROM coupons WHERE code = ?", new Object[]{code}, Integer.class) > 0;
    }

    public int count(String code, String couponName, String state){
        StringBuffer sqlWhere = new StringBuffer();
        List<Object> paramObjects = new java.util.ArrayList<Object>();

        sqlWhere.append(" WHERE 1 = 1 ");

        if(!StringUtils.isEmpty(code)){
            sqlWhere.append(" AND code = ?");
            paramObjects.add(code);
        }

        if(!StringUtils.isEmpty(couponName)){
            sqlWhere.append(" AND coupon_name like ?");
            paramObjects.add("%" + couponName + "%");
        }

        if(!StringUtils.isEmpty(state)){
            sqlWhere.append(" AND state = ?");
            paramObjects.add(state);
        }

        return jdbcTemplate.queryForObject("SELECT count(code) FROM coupons " + sqlWhere.toString(), paramObjects.toArray(), Integer.class);
    }

    public List<CouponVo> query(String code, String couponName, String state, int limit, int offset){
        StringBuffer sqlWhere = new StringBuffer();
        List<Object> paramObjects = new java.util.ArrayList<Object>();

        sqlWhere.append(" WHERE 1 = 1 ");

        if(!StringUtils.isEmpty(code)){
            sqlWhere.append(" AND code = ?");
            paramObjects.add(code);
        }

        if(!StringUtils.isEmpty(couponName)){
            sqlWhere.append(" AND coupon_name like ?");
            paramObjects.add("%" + couponName + "%");
        }

        if(!StringUtils.isEmpty(state)){
            sqlWhere.append(" AND state = ?");
            paramObjects.add(state);
        }

        sqlWhere.append(" ORDER BY created_at ");
        sqlWhere.append(" LIMIT ?");
        sqlWhere.append(" OFFSET ?");

        paramObjects.add(limit);
        paramObjects.add(offset);

        return jdbcTemplate.query("SELECT * FROM coupons " + sqlWhere.toString(), paramObjects.toArray(), couponVoRowMapper);
    }


    public void insert(Coupon coupon){
        jdbcTemplate.update("INSERT INTO coupons(code, coupon_name, valid_date_from, valid_date_to, state) VALUES(?,?,?,?,?)"
                            , coupon.getCode()
                            , coupon.getCouponName()
                            , coupon.getValidDateFrom()
                            , coupon.getValidDateTo()
                            , coupon.getState());
    }

    public void update(Coupon coupon){
        jdbcTemplate.update("UPDATE coupons SET coupon_name = ?, valid_date_from = ?, valid_date_to = ?, state = ? WHERE code = ?"
                            , coupon.getCouponName()
                            , coupon.getValidDateFrom()
                            , coupon.getValidDateTo()
                            , coupon.getState()
                            , coupon.getCode());
    }

    static class CouponVoRowMapper implements RowMapper<CouponVo>{
        @Override
        public CouponVo mapRow(ResultSet rs, int rowNum) throws SQLException {
            CouponVo couponVo = new CouponVo();

            couponVo.setCode(rs.getString("code"));
            couponVo.setCouponName(rs.getString("coupon_name"));
            couponVo.setValidDateFrom(rs.getDate("valid_date_from").toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyy")));
            couponVo.setValidDateTo(rs.getDate("valid_date_to").toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyy")));
            couponVo.setState(rs.getString("state"));

            return couponVo;
        }
    }

    static class CouponRowMapper implements RowMapper<Coupon>{
        @Override
        public Coupon mapRow(ResultSet rs, int rowNum) throws SQLException {
            Coupon coupon = new Coupon();

            coupon.setCode(rs.getString("code"));
            coupon.setCouponName(rs.getString("coupon_name"));
            coupon.setValidDateFrom(rs.getDate("valid_date_from").toLocalDate());
            coupon.setValidDateTo(rs.getDate("valid_date_to").toLocalDate());
            coupon.setState(rs.getString("state"));
            coupon.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
            coupon.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

            return coupon;
        }
    }
}
