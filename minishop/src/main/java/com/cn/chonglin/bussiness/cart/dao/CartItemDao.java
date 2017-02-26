package com.cn.chonglin.bussiness.cart.dao;

import com.cn.chonglin.bussiness.cart.domain.CartItem;
import com.cn.chonglin.bussiness.cart.vo.CartItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
    private final RowMapper<CartItemVo> cartItemVoRowMapper = new CartItemVoMapper();

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(CartItem cartItem){
        jdbcTemplate.update("INSERT INTO cart_items(cart_id, item_id, quantity, color) VALUES(?,?,?,?)"
                            , cartItem.getCartId()
                            , cartItem.getItemId()
                            , cartItem.getQuantity()
                            , cartItem.getColor());
    }

    public void update(CartItem cartItem){
        jdbcTemplate.update("UPDATE cart_items SET quantity = ?, updated_at = now() WHERE cart_id = ? and item_id = ?"
                , cartItem.getQuantity()
                , cartItem.getCartId()
                , cartItem.getItemId());
    }

    public void delete(String cartId, String itemId){
        jdbcTemplate.update("DELETE FROM cart_items WHERE cart_id = ? AND item_id = ?"
                            , cartId
                            , itemId);
    }

    public CartItem findByKey(String cartId, String itemId){
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM cart_items WHERE cart_id = ? and item_id = ?"
                    , new Object[]{cartId, itemId}, mapper);
        }catch (EmptyResultDataAccessException ex){
            return null;
        }
    }

    public List<CartItemVo> findCartItems(String cartId){
        try {
            return jdbcTemplate.query("SELECT a.cart_id, a.item_id, a.quantity, a.color" +
                    ", b.item_name, b.state, b.unit_price, b.discount_price FROM cart_items a " +
                    "INNER JOIN items b ON  a.item_id = b.item_id WHERE a.cart_id = ?", new Object[]{cartId}, cartItemVoRowMapper);
        }catch (EmptyResultDataAccessException ex){
            return null;
        }
    }


    static class CartItemMapper implements RowMapper<CartItem>{
        @Override
        public CartItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            CartItem cartItem = new CartItem();

            cartItem.setCartId(rs.getString("cart_id"));
            cartItem.setItemId(rs.getString("item_id"));
            cartItem.setQuantity(rs.getInt("quantity"));
            cartItem.setUpdatedAt(rs.getTimestamp("updated_at"));
            cartItem.setCreatedAt(rs.getTimestamp("created_at"));

            return cartItem;
        }
    }

    static class CartItemVoMapper implements RowMapper<CartItemVo>{
        @Override
        public CartItemVo mapRow(ResultSet rs, int rowNum) throws SQLException {
            CartItemVo cartItemVo= new CartItemVo();

            cartItemVo.setCartId(rs.getString("cart_id"));
            cartItemVo.setItemId(rs.getString("item_id"));
            cartItemVo.setItemName(rs.getString("item_name"));
            cartItemVo.setQuantity(rs.getInt("quantity"));
            cartItemVo.setState(rs.getString("state"));
            cartItemVo.setDiscountPrice(rs.getBigDecimal("discount_price"));
            cartItemVo.setUnitPrice(rs.getBigDecimal("unit_price"));
            cartItemVo.setColor(rs.getString("color"));

            return cartItemVo;
        }
    }
}
