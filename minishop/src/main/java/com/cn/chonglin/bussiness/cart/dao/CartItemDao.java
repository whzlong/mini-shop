package com.cn.chonglin.bussiness.cart.dao;

import com.cn.chonglin.bussiness.cart.domain.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 购物车明细Dao
 */
@Repository
public class CartItemDao {
    private final RowMapper<CartItem> mapper = new CartItemMapper();

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(CartItem cartItem){
        jdbcTemplate.update("INSERT INTO cart_items(cart_id, item_id, item_name, quantity) VALUES(?,?,?,?)"
                            , cartItem.getCartId()
                            , cartItem.getItemId()
                            , cartItem.getItemName()
                            , cartItem.getQuantity());
    }

    public void update(CartItem cartItem){
        jdbcTemplate.update("UPDATE cart_items SET quantity = ?, updated_at = now() WHERE cart_id = ? and item_id = ?"
                , cartItem.getQuantity()
                , cartItem.getCartId()
                , cartItem.getItemId());
    }

    public CartItem findByKey(String cartId, String itemId){
        return jdbcTemplate.queryForObject("SELECT * FROM cart_items WHERE cart_id = ? and item_id = ?"
                                            , new Object[]{cartId, itemId}, mapper);
    }

    public List<CartItem> findByCartId(String cartId){
        return jdbcTemplate.query("SELECT * FROM cart_items WHERE cart_id = ?", new Object[]{cartId}, mapper);
    }


    static class CartItemMapper implements RowMapper<CartItem>{
        @Override
        public CartItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            CartItem cartItem = new CartItem();

            cartItem.setCartId(rs.getString("cart_id"));
            cartItem.setItemId(rs.getString("item_id"));
            cartItem.setItemName(rs.getString("item_name"));
            cartItem.setQuantity(rs.getInt("quantity"));
            cartItem.setPrice(rs.getBigDecimal("price"));
            cartItem.setUpdatedAt(rs.getTimestamp("updated_at"));
            cartItem.setCreatedAt(rs.getTimestamp("created_at"));

            return cartItem;
        }
    }
}
