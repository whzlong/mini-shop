package com.cn.chonglin.bussiness.cart.dao;

import com.cn.chonglin.bussiness.cart.domain.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM carts WHERE cart_id = ?", new Object[]{cartId}, mapper);
        }catch (EmptyResultDataAccessException ex){
            return null;
        }
    }

    public void insert(Cart cart){
        jdbcTemplate.update("INSERT INTO carts(cart_id, total_price) VALUES(?, ?)"
                            , cart.getCartId()
                            , cart.getTotalPrice());
    }

    public void update(Cart cart){
        jdbcTemplate.update("UPDATE carts SET total_price = ? WHERE cart_id = ?"
                            , cart.getTotalPrice()
                            , cart.getCartId());
    }

    static class CartMapper implements RowMapper<Cart>{
        @Override
        public Cart mapRow(ResultSet rs, int rowNum) throws SQLException {
            Cart cart = new Cart();

            cart.setCartId(rs.getString("cart_id"));
            cart.setTotalPrice(rs.getBigDecimal("total_price"));
            cart.setUpdatedAt(rs.getTimestamp("updated_at"));
            cart.setCreatedAt(rs.getTimestamp("created_at"));

            return cart;
        }
    }
}
