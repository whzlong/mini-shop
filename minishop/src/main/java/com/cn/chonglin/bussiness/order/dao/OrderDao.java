package com.cn.chonglin.bussiness.order.dao;

import com.cn.chonglin.bussiness.order.domain.Order;
import com.cn.chonglin.bussiness.order.vo.OrderVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 订单Dao
 */
@Repository
public class OrderDao {
    private RowMapper<OrderVo> orderVoMapper = new OrderVoMapper();
    private RowMapper<Order> orderRowMapper = new OrderMapper();

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Order findByKey(long orderId){
        return jdbcTemplate.queryForObject("SELECT * FROM orders WHERE order_id = ?", new Object[]{orderId}, orderRowMapper);
    }

    public void update(Order order){
        jdbcTemplate.update("UPDATE orders SET pay_date = ?, state = ?, ship_address = ?, comment = ? WHERE order_id = ?"
                            , order.getPayDate()
                            , order.getState()
                            , order.getShipAddress()
                            , order.getComment()
                            , order.getOrderId());
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
        jdbcTemplate.update("INSERT INTO orders(order_id, user_id, pay_date, ship_address" +
                        ", total_amount,comment,state) VALUES(?,?,?,?,?,?,?)"
                , order.getOrderId()
                , order.getUserId()
                , order.getPayDate()
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
            sqlWhere.append(" AND created_at >= ?");
            paramObjects.add(LocalDate.parse(orderDateFrom, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        }

        if(!StringUtils.isEmpty(orderDateTo)){
            sqlWhere.append(" AND created_at <= ?");
            paramObjects.add(LocalDate.parse(orderDateTo, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
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
    public List<OrderVo> query(String orderId, String orderDateFrom, String orderDateTo,String state, int limit, int offset){
        StringBuffer sqlWhere = new StringBuffer();
        List<Object> paramObjects = new java.util.ArrayList<Object>();

        sqlWhere.append(" WHERE a.state = ?");
        paramObjects.add(state);

        if (!StringUtils.isEmpty(orderId)){
            sqlWhere.append(" AND a.order_id = ?");
            paramObjects.add(orderId);
        }

        if(!StringUtils.isEmpty(orderDateFrom)){
            sqlWhere.append(" AND a.created_at >= ?");
            paramObjects.add(LocalDate.parse(orderDateFrom, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        }

        if(!StringUtils.isEmpty(orderDateTo)){
            sqlWhere.append(" AND a.created_at <= ?");
            paramObjects.add(LocalDate.parse(orderDateTo, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        }

        sqlWhere.append(" ORDER BY a.created_at ");
        sqlWhere.append(" LIMIT ?");
        sqlWhere.append(" OFFSET ?");

        paramObjects.add(limit);
        paramObjects.add(offset);

        try{
            return jdbcTemplate.query("SELECT a.order_id, concat_ws(' ', b.first_name, b.last_name) user_name, " +
                    "a.pay_date, a.ship_address, a.total_amount, a.comment, a.state, a.created_at " +
                    "FROM orders a INNER JOIN users b ON a.user_id = b.id " + sqlWhere.toString(), paramObjects.toArray(), orderVoMapper);
        }catch(EmptyResultDataAccessException ex){
            return null;
        }

    }

    public void delete(long id){
        jdbcTemplate.update("DELETE FROM orders WHERE order_id = ?", id);
    }

    static class OrderMapper implements RowMapper<Order>{
        @Override
        public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
            Order order = new Order();

            order.setOrderId(rs.getLong("order_id"));
            order.setUserId(rs.getString("user_id"));
            if(rs.getDate("pay_date") != null){
                order.setPayDate(rs.getDate("pay_date").toLocalDate());
            }
            order.setShipAddress(rs.getString("ship_address"));
            order.setTotalAmount(rs.getBigDecimal("total_amount"));
            order.setState(rs.getString("state"));
            order.setComment(rs.getString("comment"));
            order.setUpdatedAt(rs.getTimestamp("updated_at"));
            order.setCreatedAt(rs.getTimestamp("created_at"));

            return order;
        }
    }

    static class OrderVoMapper implements RowMapper<OrderVo>{
        @Override
        public OrderVo mapRow(ResultSet rs, int rowNum) throws SQLException {
            OrderVo orderVo = new OrderVo();

            orderVo.setOrderId(rs.getLong("order_id"));
            orderVo.setUserName(rs.getString("user_name"));

            if(rs.getDate("pay_date") != null){
                orderVo.setPayDate(rs.getDate("pay_date").toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            }

            orderVo.setShipAddress(rs.getString("ship_address"));
            orderVo.setTotalAmount(rs.getBigDecimal("total_amount"));
            orderVo.setComment(rs.getString("comment"));
            orderVo.setState(rs.getString("state"));
            orderVo.setOrderDatetime(rs.getTimestamp("created_at").toLocalDateTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy a hh:MM")));

            return orderVo;
        }
    }
}
