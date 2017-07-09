package com.cn.chonglin.bussiness.appointment.dao;


import com.cn.chonglin.bussiness.appointment.domain.Appointment;
import com.cn.chonglin.bussiness.appointment.vo.AppointmentVo;
import com.cn.chonglin.bussiness.appointment.vo.SimpleAppointmentVo;
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
 * 预约Dao
 */
@Repository
public class AppointmentDao {
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Appointment> appointmentRowMapper = new AppointmentMapper();

    private final RowMapper<AppointmentVo> appointmentVoRowMapper = new AppointmentVoMapper();

    private final RowMapper<SimpleAppointmentVo> simpleAppointmentVoRowMapper = new SimpleAppointmentVoMapper();

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

    public void delete(String id){
        jdbcTemplate.update("DELETE FROM appointments WHERE id = ?"
                            , id);
    }

    public Appointment findByKey(String id){
        return jdbcTemplate.queryForObject("SELECT * FROM appointments WHERE id = ?", new Object[]{id}, appointmentRowMapper);
    }

    public int countAppointments(String bookDateFrom, String bookDateTo, String state){
        StringBuilder whereSql = new StringBuilder();
        List<Object> parameters = new java.util.ArrayList<>();

        whereSql.append("WHERE state = ? ");
        parameters.add(state);

        if(!StringUtils.isEmpty(bookDateFrom)){
            whereSql.append("AND book_date >= ? ");
            LocalDate localDate = LocalDate.parse(bookDateFrom, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

            parameters.add(localDate);
        }

        if(!StringUtils.isEmpty(bookDateTo)){
            whereSql.append("AND book_date <= ? ");
            LocalDate localDate = LocalDate.parse(bookDateTo, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

            parameters.add(localDate);
        }

        return jdbcTemplate.queryForObject("SELECT count(id) FROM appointments " + whereSql.toString()
                , parameters.toArray()
                , Integer.class);
    }

    public List<AppointmentVo> queryForList(String bookDateFrom, String bookDateTo,String state, int limit, int offset){
        StringBuilder whereSql = new StringBuilder();
        List<Object> parameters = new java.util.ArrayList<>();

        whereSql.append("WHERE a.state = ? ");
        parameters.add(state);

        if(!StringUtils.isEmpty(bookDateFrom)){
            whereSql.append("AND a.book_date >= ? ");
            LocalDate localDate = LocalDate.parse(bookDateFrom, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

            parameters.add(localDate);
        }

        if(!StringUtils.isEmpty(bookDateTo)){
            whereSql.append("AND a.book_date <= ? ");
            LocalDate localDate = LocalDate.parse(bookDateTo, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

            parameters.add(localDate);
        }

        parameters.add(limit);
        parameters.add(offset);

        try{
            return jdbcTemplate.query("SELECT a.id, concat_ws(' ', b.first_name, b.last_name) AS user_name," +
                    " b.phone, c.item_name, a.book_date, a.book_time, a.state, a.comment " +
                    "FROM appointments a " +
                    "INNER JOIN users b ON a.user_id = b.id " +
                    "INNER JOIN items c ON a.item_id = c.item_id " +
                    whereSql.toString() +
                    "ORDER BY a.book_date, a.book_time " +
                    "LIMIT ? OFFSET ?", parameters.toArray(), appointmentVoRowMapper);

        }catch (EmptyResultDataAccessException ex){
            return null;
        }

    }

    /**
     * 查询特定客户某个期间的预约
     *
     * @param userId
     * @param dateFrom
     * @return
     */
    public List<SimpleAppointmentVo> queryByPeriod(String userId, LocalDate dateFrom){
        try{
            return jdbcTemplate.query("SELECT a.id, a.book_date, a.book_time, b.item_name, b.item_list_image, b.model_name FROM appointments a INNER JOIN items b " +
                    "ON a.item_id = b.item_id WHERE a.user_id = ? AND a.book_date >= ?"
                    , new Object[]{userId, dateFrom}, simpleAppointmentVoRowMapper);
        }catch (EmptyResultDataAccessException ex){
            return null;
        }
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

    static class SimpleAppointmentVoMapper implements RowMapper<SimpleAppointmentVo>{
        @Override
        public SimpleAppointmentVo mapRow(ResultSet rs, int rowNum) throws SQLException {
            SimpleAppointmentVo vo = new SimpleAppointmentVo();

            vo.setId(rs.getString("id"));
            vo.setBookDate(rs.getDate("book_date").toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            vo.setBookTime(rs.getTime("book_time").toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
            vo.setItemName(rs.getString("item_name"));
            vo.setItemListImage(rs.getString("item_list_image"));
            vo.setModelName(rs.getString("model_name"));

            return vo;
        }
    }
}
