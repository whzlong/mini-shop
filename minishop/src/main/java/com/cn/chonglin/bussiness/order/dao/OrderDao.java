package com.cn.chonglin.bussiness.order.dao;

import com.cn.chonglin.bussiness.order.domain.Order;
import com.cn.chonglin.bussiness.order.vo.OrderVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 订单Dao
 */
@Repository
public class OrderDao {
    private RowMapper<OrderVo> orderVoMapper = new OrderVoMapper();


    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * 订单号存在检查
     *
     * @param orderId
     * @return
     */
    public int checkOrderId(long orderId){
        return jdbcTemplate.queryForObject("SELECT COUNT(order_id) FROM orders WHERE order_id = ?"
                , new Object[]{orderId}, Integer.class);
    }

    public void insert(Order order){
        jdbcTemplate.update("INSERT INTO orders(order_id, customer_id, customer_name, order_date, order_time, ship_address" +
                        ", total_amount,comment,state) VALUES(?,?,?,?,?,?,?,?,?)"
                , order.getOrderId()
                , order.getCustomerId()
                , order.getCustomerName()
                , order.getOrderDate()
                , order.getOrderTime()
                , order.getShipAddress()
                , order.getTotalAmount()
                , order.getComment()
                , order.getState()
        );
    }

    /**
     * 取List数据量
     *
     * @param orderId
     * @param orderDateFrom
     * @param orderDateTo
     * @param state
     * @return
     */
    public int getRecordCount(String orderId, String orderDateFrom, String orderDateTo, String state){
        StringBuffer sqlWhere = new StringBuffer();
        List<Object> paramObjects = new java.util.ArrayList<Object>();

        sqlWhere.append(" WHERE state = ?");
        paramObjects.add(state);

        if (!StringUtils.isEmpty(orderId)){
            sqlWhere.append(" AND order_id = ?");
            paramObjects.add(orderId);
        }

        if(!StringUtils.isEmpty(orderDateFrom)){
            sqlWhere.append(" AND order_date >= ?");
            paramObjects.add(orderDateFrom);
        }

        if(!StringUtils.isEmpty(orderDateTo)){
            sqlWhere.append(" AND order_date <= ?");
            paramObjects.add(orderDateTo);
        }


        Object[] params = new Object[paramObjects.size()];

        for(int i = 0; i < paramObjects.size(); i++){
            params[i] = paramObjects.get(i);
        }

        return jdbcTemplate.queryForObject("SELECT COUNT(order_id) FROM orders " + sqlWhere.toString(), params, Integer.class);
    }

    /**
     * 查询订单
     *
     * @param orderId
     *          订单号
     * @param orderDateFrom
     *          订单起始日期
     * @param orderDateTo
     *          订单结束日期
     * @param state
     *          状态
     * @param limit
     *          列表每页大小
     * @param offset
     *          显示页起始
     * @return
     */
    public List<OrderVo> queryForList(String orderId, String orderDateFrom, String orderDateTo,String state, int limit, int offset){
        StringBuffer sqlWhere = new StringBuffer();
        List<Object> paramObjects = new java.util.ArrayList<Object>();

        sqlWhere.append(" WHERE state = ?");
        paramObjects.add(state);

        if (!StringUtils.isEmpty(orderId)){
            sqlWhere.append(" AND order_id = ?");
            paramObjects.add(orderId);
        }

        if(!StringUtils.isEmpty(orderDateFrom)){
            sqlWhere.append(" AND order_date >= ?");
            paramObjects.add(orderDateFrom);
        }

        if(!StringUtils.isEmpty(orderDateTo)){
            sqlWhere.append(" AND order_date <= ?");
            paramObjects.add(orderDateTo);
        }

        sqlWhere.append(" ORDER BY created_at ");
        sqlWhere.append(" LIMIT ?");
        sqlWhere.append(" OFFSET ?");

        paramObjects.add(limit);
        paramObjects.add(offset);

        Object[] params = new Object[paramObjects.size()];

        for(int i = 0; i < paramObjects.size(); i++){
            params[i] = paramObjects.get(i);
        }

        return jdbcTemplate.query("SELECT * FROM orders " + sqlWhere.toString(), params, orderVoMapper);
    }

    public void delete(long id){
        jdbcTemplate.update("DELETE FROM orders WHERE order_id = ?", id);
    }

    static class OrderMapper implements RowMapper<Order>{
        @Override
        public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
            Order order = new Order();

            order.setOrderId(rs.getLong("order_id"));
            order.setCustomerId(rs.getString("customer_id"));
            order.setOrderDate(rs.getString("order_date"));

            return null;
        }
    }

    static class OrderVoMapper implements RowMapper<OrderVo>{
        @Override
        public OrderVo mapRow(ResultSet rs, int rowNum) throws SQLException {
            OrderVo orderVo = new OrderVo();

            orderVo.setOrderId(rs.getLong("order_id"));
            orderVo.setDatetime(rs.getString("order_date") + " " + rs.getString("order_time"));
            orderVo.setCustomerName(rs.getString("customer_name"));
            orderVo.setTotalAmount(rs.getBigDecimal("total_amount"));
            orderVo.setComment(rs.getString("comment"));
            orderVo.setState(rs.getString("state"));

            return orderVo;
        }
    }
}
