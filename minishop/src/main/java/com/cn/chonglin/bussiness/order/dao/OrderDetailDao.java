package com.cn.chonglin.bussiness.order.dao;

import com.cn.chonglin.bussiness.order.domain.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 订单明细Dao
 */
@Repository
public class OrderDetailDao {
    private RowMapper<OrderDetail> mapper = new OrderDetailMapper();

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDateSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(OrderDetail orderDetail){
        jdbcTemplate.update("INSERT INTO order_details(order_id, item_id, item_name, quantity, unit_price, discount_price) VALUES(?,?,?,?,?,?)"
                , orderDetail.getOrderId()
                , orderDetail.getItemId()
                , orderDetail.getItemName()
                , orderDetail.getQuantity()
                , orderDetail.getUnitPrice()
                , orderDetail.getDiscountPrice());
    }

    public List<OrderDetail> findByKey(long orderId){
        return jdbcTemplate.query("SELECT * FROM order_details WHERE order_id = ?", new Object[]{orderId}, mapper);
    }

    public void delete(long orderId){
        jdbcTemplate.update("DELETE FROM order_details WHERE order_id = ?", orderId);
    }

    static final class OrderDetailMapper implements RowMapper<OrderDetail>{
        @Override
        public OrderDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
            OrderDetail orderDetail = new OrderDetail();

            orderDetail.setOrderId(rs.getLong("order_id"));
            orderDetail.setItemId(rs.getString("item_id"));
            orderDetail.setItemName(rs.getString("item_name"));
            orderDetail.setQuantity(rs.getInt("quantity"));
            orderDetail.setUnitPrice(rs.getBigDecimal("unit_price"));
            orderDetail.setDiscountPrice(rs.getBigDecimal("discount_price"));
            orderDetail.setUpdatedAt(rs.getTimestamp("updated_at"));
            orderDetail.setCreatedAt(rs.getTimestamp("created_at"));

            return orderDetail;
        }
    }
}
