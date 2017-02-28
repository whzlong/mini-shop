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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 预约Dao
 */
@Repository
public class AppointmentDao {
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Appointment> appointmentRowMapper = new AppointmentMapper();

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

    public Appointment findByKey(String id){
        return jdbcTemplate.queryForObject("SELECT * FROM appointments WHERE id = ?", new Object[]{id}, appointmentRowMapper);
    }

    public int countAppointments(String bookDate, String state){
        StringBuilder whereSql = new StringBuilder();
        List<Object> parameters = new java.util.ArrayList<>();

        whereSql.append("WHERE state = ? ");
        parameters.add(state);

        if(!StringUtils.isEmpty(bookDate)){
            whereSql.append("AND book_date = ? ");
            LocalDate localDate = LocalDate.parse(bookDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

            parameters.add(localDate);
        }

        return jdbcTemplate.queryForObject("SELECT count(id) FROM appointments " + whereSql.toString()
                , parameters.toArray()
                , Integer.class);
    }

    public List<AppointmentVo> queryForList(String bookDate, String state, int limit, int offset){
        StringBuilder whereSql = new StringBuilder();
        List<Object> parameters = new java.util.ArrayList<>();

        whereSql.append("WHERE a.state = ? ");
        parameters.add(state);

        if(!StringUtils.isEmpty(bookDate)){
            whereSql.append("AND a.book_date = ? ");
            LocalDate localDate = LocalDate.parse(bookDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

            parameters.add(localDate);
        }

        parameters.add(limit);
        parameters.add(offset);

        return jdbcTemplate.query("SELECT a.id, concat_ws(' ', b.first_name, b.last_name) AS user_name," +
                " b.phone, c.item_name, a.book_date, a.book_time, a.state, a.comment " +
                "FROM appointments a " +
                "INNER JOIN users b ON a.user_id = b.id " +
                "INNER JOIN items c ON a.item_id = c.item_id " +
                whereSql.toString() +
                "ORDER BY a.book_date, a.book_time " +
                "LIMIT ? OFFSET ?", parameters.toArray(), appointmentVoRowMapper);
    }

    public void update(Appointment appointment){
        jdbcTemplate.update("UPDATE appointments SET book_date = ?, book_time = ?, state = ?, comment= ? WHERE id = ?"
                            , appointment.getBookDate()
                            , appointment.getBookTime()
                            , appointment.getState()
                            , appointment.getComment()
                            , appointment.getId());
    }

    static class AppointmentMapper implements RowMapper<Appointment>{
        @Override
        public Appointment mapRow(ResultSet rs, int rowNum) throws SQLException {
            Appointment appointment = new Appointment();

            appointment.setId(rs.getString("id"));
            appointment.setBookDate(rs.getDate("book_date").toLocalDate());
            appointment.setBookTime(rs.getTime("book_time").toLocalTime());
            appointment.setItemId(rs.getString("item_id"));
            appointment.setUserId(rs.getString("user_id"));
            appointment.setState(rs.getString("state"));
            appointment.setComment(rs.getString("comment"));
            appointment.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
            appointment.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());

            return appointment;
        }
    }

    static class AppointmentVoMapper implements RowMapper<AppointmentVo>{
        @Override
        public AppointmentVo mapRow(ResultSet rs, int rowNum) throws SQLException {
            AppointmentVo appointmentVo = new AppointmentVo();

            appointmentVo.setId(rs.getString("id"));
            appointmentVo.setUserName(rs.getString("user_name"));
            appointmentVo.setItemName(rs.getString("item_name"));
            appointmentVo.setPhone(rs.getString("phone"));
            appointmentVo.setBookDate(rs.getDate("book_date").toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            appointmentVo.setBookTime(rs.getTime("book_time").toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
            appointmentVo.setState(rs.getString("state"));
            appointmentVo.setComment(rs.getString("comment"));

            return appointmentVo;
        }
    }
}
