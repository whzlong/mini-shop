package com.cn.chonglin.bussiness.order.dao;

import com.cn.chonglin.bussiness.order.domain.OrderDetail;
import com.cn.chonglin.bussiness.order.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 订单明细Dao
 */
@Repository
public class OrderDetailDao {
    private RowMapper<OrderDetailVo> mapper = new OrderDetailVoMapper();

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDateSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(OrderDetail orderDetail){
        jdbcTemplate.update("INSERT INTO order_details(order_id, item_id, quantity, order_price) VALUES(?,?,?,?)"
                , orderDetail.getOrderId()
                , orderDetail.getItemId()
                , orderDetail.getQuantity()
                , orderDetail.getOrderPrice());
    }

    public List<OrderDetailVo> query(long orderId){
        return jdbcTemplate.query("SELECT a.order_id, a.item_id, b.item_name, a.quantity, b.unit_price, " +
                "b.discount_price, a.order_price, b.state item_state FROM order_details a INNER JOIN items b ON a.item_id = b.item_id WHERE order_id = ?"
                , new Object[]{orderId}, mapper);
    }

    public void delete(long orderId){
        jdbcTemplate.update("DELETE FROM order_details WHERE order_id = ?", orderId);
    }

    public void updateOrderPrice(long orderId, String itemId, BigDecimal orderPrice){
        jdbcTemplate.update("UPDATE order_Details SET order_price = ? WHERE order_id = ? AND item_id = ?"
                            , orderPrice
                            , orderId
                            , itemId);
    }

    static final class OrderDetailVoMapper implements RowMapper<OrderDetailVo>{
        @Override
        public OrderDetailVo mapRow(ResultSet rs, int rowNum) throws SQLException {
            OrderDetailVo orderDetail = new OrderDetailVo();

            orderDetail.setOrderId(rs.getLong("order_id"));
            orderDetail.setItemId(rs.getString("item_id"));
            orderDetail.setItemName(rs.getString("item_name"));
            orderDetail.setQuantity(rs.getInt("quantity"));
            orderDetail.setUnitPrice(rs.getBigDecimal("unit_price"));
            orderDetail.setDiscountPrice(rs.getBigDecimal("discount_price"));
            orderDetail.setOrderPrice(rs.getBigDecimal("order_price"));
            orderDetail.setItemState(rs.getString("item_state"));

            return orderDetail;
        }
    }
}
