package com.cn.chonglin.bussiness.cart.dao;

import com.cn.chonglin.bussiness.cart.domain.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *  购物车Dao
 */
@Repository
public class CartDao {
    private final RowMapper<Cart> mapper = new CartMapper();

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Cart findByKey(String cartId){
        return jdbcTemplate.queryForObject("SELECT * FROM carts WHERE cart_id = ?", new Object[]{cartId}, mapper);
    }

    public void insert(Cart cart){
        jdbcTemplate.update("INSERT INTO carts(cart_id, user_id, total_price) VALUES(?, ?, ?)"
                            , cart.getCartId()
                            , cart.getUserId()
                            , cart.getTotalPrice());
    }

    static class CartMapper implements RowMapper<Cart>{
        @Override
        public Cart mapRow(ResultSet rs, int rowNum) throws SQLException {
            Cart cart = new Cart();

            cart.setCartId(rs.getString("cart_id"));
            cart.setUserId(rs.getString("user_id"));
            cart.setTotalPrice(rs.getBigDecimal("total_price"));
            cart.setUpdatedAt(rs.getTimestamp("updated_at"));
            cart.setCreatedAt(rs.getTimestamp("created_at"));

            return cart;
        }
    }
}
