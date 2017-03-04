package com.cn.chonglin.bussiness.item.dao;

import com.cn.chonglin.bussiness.item.domain.Item;
import com.cn.chonglin.bussiness.item.vo.ItemVo;
import org.apache.commons.lang3.StringUtils;
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
    private final RowMapper<ItemVo> itemVoRowMapper = new ItemVoMapper();

    private JdbcTemplate jdbcTemplate;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    public Item findByKey(String id){
        return jdbcTemplate.queryForObject("SELECT * FROM items WHERE item_id = ?", new Object[]{id}, mapper);
    }

    public List<Item> findNewItems(){
        return jdbcTemplate.query("SELECT * FROM items WHERE state = 'new'", new Object[]{}, mapper);
    }

    public List<Item> findDiscountItems(){
        return jdbcTemplate.query("SELECT * FROM items WHERE state = 'discount'", new Object[]{}, mapper);
    }

    public List<Item> findItemsByModel(String modelId){
        try{
            return jdbcTemplate.query("SELECT * FROM items WHERE model_id = ?", new Object[]{modelId}, mapper);
        }catch (EmptyResultDataAccessException ex){
            return null;
        }
    }

    /**
     * 列表记录数量
     *
     * @param brand
     * @param model
     * @return
     */
    public int countItems(String brand, String model){
        StringBuffer sqlWhere = new StringBuffer();
        List<Object> paramObjects = new java.util.ArrayList<Object>();

        sqlWhere.append(" WHERE 1 = 1");

        if (!StringUtils.isEmpty(brand)){
            sqlWhere.append(" AND brand_id = ?");
            paramObjects.add(brand);
        }

        if (!StringUtils.isEmpty(model)){
            sqlWhere.append(" AND model_id = ?");
            paramObjects.add(brand);
        }

        return jdbcTemplate.queryForObject("SELECT COUNT(item_id) FROM items " + sqlWhere.toString(), paramObjects.toArray(), Integer.class);
    }

    public List<ItemVo> query(String brand, String model, int limit, int offset){
        StringBuffer sqlWhere = new StringBuffer();
        List<Object> paramObjects = new java.util.ArrayList<Object>();

        sqlWhere.append(" WHERE 1 = 1");

        if (!StringUtils.isEmpty(brand)){
            sqlWhere.append(" AND brand_id = ?");
            paramObjects.add(brand);
        }

        if (!StringUtils.isEmpty(model)){
            sqlWhere.append(" AND model_id = ?");
            paramObjects.add(brand);
        }

        sqlWhere.append(" ORDER BY created_at ");
        sqlWhere.append(" LIMIT ?");
        sqlWhere.append(" OFFSET ?");

        paramObjects.add(limit);
        paramObjects.add(offset);

        return jdbcTemplate.query("SELECT * FROM items " + sqlWhere.toString(), paramObjects.toArray(), itemVoRowMapper);
    }

    public void insert(Item item){
        jdbcTemplate.update("INSERT INTO items(item_id, item_name, item_list_image, item_detail_image" +
                ", unit_price, discount_price, description, brand_id, brand_name" +
                ", model_id, model_name, stock, state) values(?,?,?,?,?,?,?,?,?,?,?,?,?)"
         , item.getItemId()
         , item.getItemName()
         , item.getItemListImage()
         , item.getItemDetailImage()
         , item.getUnitPrice()
         , item.getDiscountPrice()
         , item.getDescription()
         , item.getBrandId()
         , item.getBrandName()
         , item.getModelId()
         , item.getModelName()
         , item.getStock()
         , item.getState());
    }

    public void update(Item item){

        jdbcTemplate.update("UPDATE items SET item_name = ?, item_list_image = ?, item_detail_image = ?" +
                        ", unit_price = ?, discount_price = ?, brand_id = ?, brand_name = ?" +
                        ", model_id = ?, model_name = ?, stock = ?, state = ?, updated_at = now()  WHERE item_id = ?"
                , item.getItemName()
                , item.getItemListImage()
                , item.getItemDetailImage()
                , item.getUnitPrice()
                , item.getDiscountPrice()
                , item.getBrandId()
                , item.getBrandName()
                , item.getModelId()
                , item.getModelName()
                , item.getStock()
                , item.getState()
                , item.getItemId());

    }

    static class ItemMapper implements RowMapper<Item> {

        @Override
        public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
            Item t = new Item();

            t.setItemId(rs.getString("item_id"));
            t.setItemName(rs.getString("item_name"));
            t.setItemListImage(rs.getString("item_list_image"));
            t.setItemDetailImage(rs.getString("item_detail_image"));
            t.setUnitPrice(rs.getBigDecimal("unit_price"));
            t.setDiscountPrice(rs.getBigDecimal("discount_price"));
            t.setBrandId(rs.getString("brand_id"));
            t.setBrandName(rs.getString("brand_name"));
            t.setModelId(rs.getString("model_id"));
            t.setModelName(rs.getString("model_name"));
            t.setDescription(rs.getString("description"));
            t.setStock(rs.getInt("stock"));
            t.setState(rs.getString("state"));
            t.setUpdatedAt(rs.getTimestamp("updated_at"));
            t.setCreatedAt(rs.getTimestamp("created_at"));

            return t;
        }
    }

    static class ItemVoMapper implements RowMapper<ItemVo> {

        @Override
        public ItemVo mapRow(ResultSet rs, int rowNum) throws SQLException {
            ItemVo t = new ItemVo();

            t.setItemId(rs.getString("item_id"));
            t.setItemName(rs.getString("item_name"));
            t.setUnitPrice(rs.getBigDecimal("unit_price"));
            t.setDiscountPrice(rs.getBigDecimal("discount_price"));
            t.setBrandId(rs.getString("brand_id"));
            t.setBrandName(rs.getString("brand_name"));
            t.setModelId(rs.getString("model_id"));
            t.setModelName(rs.getString("model_name"));
            t.setStock(rs.getInt("stock"));
            t.setState(rs.getString("state"));

            return t;
        }
    }
}
