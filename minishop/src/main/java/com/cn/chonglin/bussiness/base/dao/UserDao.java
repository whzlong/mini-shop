package com.cn.chonglin.bussiness.base.dao;

import com.cn.chonglin.bussiness.base.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 用户Dao
 *
 * @author wu
 */
@Repository
public class UserDao {
    private final RowMapper<User> mapper = new UserDao.UserMapper();
    private JdbcTemplate jdbcTemplate;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    public User findByKey(String id){
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ?", new Object[]{id}, mapper);
        }catch (EmptyResultDataAccessException ex){
            return null;
        }
    }

    public User findByEmail(String email){
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email = ?", new Object[]{email}, mapper);
        }catch (EmptyResultDataAccessException ex){
            return null;
        }
    }

    public void insert(User user){
        StringBuffer sql = new StringBuffer();

        sql.append("INSERT INTO users(");
        sql.append("id");
        sql.append(", email");
        sql.append(", password");
        sql.append(", first_name");
        sql.append(", last_name");
        sql.append(", postcode");
        sql.append(", address");
        sql.append(", phone");
        sql.append(", enabled");
        sql.append(", role");
        sql.append(") values(");
        sql.append("?,?,?,?,?,?,?,?,?,?)");

        jdbcTemplate.update(sql.toString()
                , user.getId()
                , user.getEmail()
                , user.getPassword()
                , user.getFirstName()
                , user.getLastName()
                , user.getPostcode()
                , user.getAddress()
                , user.getPhone()
                , user.getEnabled()
                , user.getRole());

    }

    public void updateEnabled(String id, int enabled){
        jdbcTemplate.update("UPDATE users SET enabled = ? WHERE id = ?", enabled, id);
    }

    static class UserMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User t = new User();
            t.setId(rs.getString("id"));
            t.setEmail(rs.getString("email"));
            t.setFirstName(rs.getString("first_name"));
            t.setLastName(rs.getString("last_name"));
            t.setAddress(rs.getString("address"));
            t.setPassword(rs.getString("password"));
            t.setPhone(rs.getString("phone"));
            t.setPostcode(rs.getString("postcode"));
            t.setState(rs.getString("state"));
            t.setEnabled(rs.getInt("enabled"));
            t.setRole(rs.getString("role"));
            t.setUpdatedAt(rs.getTimestamp("updated_at"));
            t.setCreatedAt(rs.getTimestamp("created_at"));

            return t;
        }
    }

}
