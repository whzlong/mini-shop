package com.cn.chonglin.bussiness.item.dao;

import com.cn.chonglin.bussiness.item.domain.ItemCategory;
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
 * 商品类别Dao
 *
 * @author wu
 */
@Repository
public class ItemCategoryDao {
    private final RowMapper<ItemCategory> mapper = new ItemCategoryMapper();
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDateSource(DataSource dataSource){

        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public ItemCategory findByKey(String categoryId){
        return jdbcTemplate.queryForObject("SELECT * FROM item_categories WHERE category_id = ?", new Object[]{categoryId}, mapper);
    }
    /**
     * 查找商品子类别
     *
     * @param parentCategoryId
     *          所属商品类别ID
     * @return
     */
    public List<ItemCategory> findByParentCategoryId(String parentCategoryId){
        try{
            return jdbcTemplate.query("SELECT * FROM item_categories WHERE parent_category_id = ?", new Object[]{parentCategoryId}, mapper);
        }catch (EmptyResultDataAccessException ex){
            return null;
        }
    }


    /**
     * 查找品牌
     *
     * @return
     */
    public List<ItemCategory> findBrands(){
        try{
            return jdbcTemplate.query("SELECT * FROM item_categories WHERE parent_category_id = '0'", new Object[]{}, mapper);
        }catch (EmptyResultDataAccessException ex){
            return null;
        }
    }

    /**
     * 取名称
     * @param typeId
     * @return
     */
    public String getItemCategoryName(String typeId){
        try{
            return jdbcTemplate.queryForObject("SELECT name FROM item_categories WHERE category_id = ?", new Object[]{typeId}, String.class);
        }catch (EmptyResultDataAccessException ex){
            return "";
        }
    }

    public void insert(String categoryId, String parentCategoryId, String name){
        jdbcTemplate.update("INSERT INTO item_categories(category_id, parent_category_id, name) VALUES(?,?,?)"
                            , categoryId
                            , parentCategoryId
                            , name);
    }

    public void update(String categoryId, String name){
        jdbcTemplate.update("UPDATE item_categories SET name = ? WHERE category_id = ?"
                            , name
                            , categoryId);
    }

    /**
     * 删除Brand数据
     *
     * @param categoryId
     */
    public void delete(String categoryId){
        jdbcTemplate.update("DELETE FROM item_categories WHERE category_id = ?", categoryId);
    }


    public void deleteByParentCategoryId(String parentCategoryId){
        jdbcTemplate.update("DELETE FROM item_categories WHERE parent_category_id = ?", parentCategoryId);
    }

    static class ItemCategoryMapper implements RowMapper<ItemCategory>{
        @Override
        public ItemCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
            ItemCategory itemCategory = new ItemCategory();

            itemCategory.setCategoryId(rs.getString("category_id"));
            itemCategory.setParentCategoryId(rs.getString("parent_category_id"));
            itemCategory.setName(rs.getString("name"));
            itemCategory.setCreatedAt(rs.getTimestamp("created_at"));

            return itemCategory;
        }
    }
}
