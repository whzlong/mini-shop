package com.cn.chonglin.bussiness.item.dao;

import com.cn.chonglin.bussiness.item.domain.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 商品Dao
 *
 * @author wu
 */
@Repository
public class ItemDao {
    private final RowMapper<Item> mapper = new ItemMapper();
    private JdbcTemplate jdbcTemplate;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    public Item findByKey(long id){
        return jdbcTemplate.queryForObject("SELECT * FROM items WHERE item_id = ?", new Object[]{id}, mapper);
    }

    public List<Item> findNewItems(boolean isNew){
        return jdbcTemplate.query("SELECT * FROM items WHERE is_new = ?", new Object[]{isNew}, mapper);
    }

    public List<Item> findDiscountItems(boolean isDiscount){
        return jdbcTemplate.query("SELECT * FROM items WHERE is_discount = ?", new Object[]{isDiscount}, mapper);
    }

    public List<Item> findItemsByModel(String modelId){
        try{
            return jdbcTemplate.query("SELECT * FROM items WHERE type_id = ?", new Object[]{modelId}, mapper);
        }catch (EmptyResultDataAccessException ex){
            return null;
        }
    }

    static class ItemMapper implements RowMapper<Item> {

        @Override
        public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
            Item t = new Item();
            t.setItemId(rs.getString("item_id"));
            t.setItemName(rs.getString("item_name"));
            t.setSmallImage(rs.getString("small_image"));
            t.setBigImage(rs.getString("big_image"));
            t.setUnitPrice(rs.getBigDecimal("unit_price"));
            t.setDiscountPrice(rs.getBigDecimal("discount_price"));
            t.setCurrency(rs.getString("currency"));
            t.setNew(rs.getBoolean("is_new"));
            t.setDiscount(rs.getBoolean("is_discount"));
            t.setDescription(rs.getString("description"));
            t.setModelId(rs.getString("type_id"));
            t.setEnabled(rs.getInt("enabled"));
            t.setUpdatedAt(rs.getTimestamp("updated_at"));
            t.setCreatedAt(rs.getTimestamp("created_at"));

            return t;
        }
    }
}
