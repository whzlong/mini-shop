package com.cn.chonglin.bussiness.appointment.dao;


import com.cn.chonglin.bussiness.appointment.domain.Appointment;
import com.cn.chonglin.bussiness.appointment.vo.AppointmentVo;
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
 * 预约Dao
 */
@Repository
public class AppointmentDao {
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<AppointmentVo> appointmentVoRowMapper = new AppointmentVoMapper();

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public void insert(Appointment appointment){
        jdbcTemplate.update("INSERT INTO appointments(id, user_id, item_id, book_date, book_time, state) VALUES(?,?,?,?,?,?)"
                            , appointment.getId()
                            , appointment.getUserId()
                            , appointment.getItemId()
                            , appointment.getBookDate()
                            , appointment.getBookTime()
                            , appointment.getState());
    }

    public int countAppointments(String bookDate, String state){
        StringBuffer whereSql = new StringBuffer();
        List<Object> parameters = new java.util.ArrayList<>();

        whereSql.append("WHERE state = ? ");
        parameters.add(state);

        if(!StringUtils.isEmpty(bookDate)){
            whereSql.append("AND bookDate = ? ");
            parameters.add(bookDate);
        }

        return jdbcTemplate.queryForObject("SELECT count(id) FROM appointments " + whereSql.toString()
                , parameters.toArray()
                , Integer.class);
    }

    public List<AppointmentVo> queryForList(String bookDate, String state, int limit, int offset){
        StringBuffer whereSql = new StringBuffer();
        List<Object> parameters = new java.util.ArrayList<>();

        whereSql.append("WHERE a.state = ? ");
        parameters.add(state);

        if(!StringUtils.isEmpty(bookDate)){
            whereSql.append("AND a.bookDate = ? ");
            parameters.add(bookDate);
        }

        parameters.add(limit);
        parameters.add(offset);

        return jdbcTemplate.query("SELECT a.id, concat_ws(' ', b.first_name, b.last_name) AS user_name, c.item_name, a.book_date, a.book_time, a.state " +
                "FROM appointments a " +
                "INNER JOIN users b ON a.user_id = b.id " +
                "INNER JOIN items c ON a.item_id = c.item_id " +
                whereSql.toString() +
                "ORDER BY a.book_date, a.book_time " +
                "LIMIT ? OFFSET ?", parameters.toArray(), appointmentVoRowMapper);
    }

    static class AppointmentVoMapper implements RowMapper<AppointmentVo>{
        @Override
        public AppointmentVo mapRow(ResultSet rs, int rowNum) throws SQLException {
            AppointmentVo appointmentVo = new AppointmentVo();

            appointmentVo.setId(rs.getString("id"));
            appointmentVo.setUserName(rs.getString("user_name"));
            appointmentVo.setItemName(rs.getString("item_name"));
            appointmentVo.setBookDate(rs.getString("book_date"));
            appointmentVo.setBookTime(rs.getString("book_time"));
            appointmentVo.setState(rs.getString("state"));

            return appointmentVo;
        }
    }
}
