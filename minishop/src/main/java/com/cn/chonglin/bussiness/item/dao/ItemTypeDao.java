package com.cn.chonglin.bussiness.item.dao;

import com.cn.chonglin.bussiness.item.domain.ItemType;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ItemTypeDao {
    private final RowMapper<ItemType> mapper = new ItemTypeMapper();
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDateSource(DataSource dataSource){

        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * 查找商品类别
     *
     * @param parentTypeId
     *          所属商品类别ID
     * @return
     */
    public List<ItemType> findItemTypes(String parentTypeId){
        return jdbcTemplate.query("SELECT * FROM item_types WHERE parent_type_id = ?", new Object[]{parentTypeId}, mapper);
    }

    static class ItemTypeMapper implements RowMapper<ItemType>{
        @Override
        public ItemType mapRow(ResultSet rs, int rowNum) throws SQLException {
            ItemType itemType = new ItemType();

            itemType.setTypeId(rs.getString("type_id"));
            itemType.setParentTypeId(rs.getString("parent_type_id"));
            itemType.setName(rs.getString("name"));
            itemType.setCreatedAt(rs.getTimestamp("created_at"));

            return itemType;
        }
    }
}
