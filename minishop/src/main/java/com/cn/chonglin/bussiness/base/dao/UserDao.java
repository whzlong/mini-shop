package com.cn.chonglin.bussiness.base.dao;

import com.cn.chonglin.bussiness.base.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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

    public User findValidUser(String email){
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email = ? AND enabled = 0", new Object[]{email}, mapper);
        }catch (EmptyResultDataAccessException ex){
            return null;
        }
    }

    public int count(String email, String firstname, int state){
        StringBuffer sqlWhere = new StringBuffer();
        List<Object> paramObjects = new java.util.ArrayList<Object>();

        sqlWhere.append(" WHERE enabled = ? ");
        paramObjects.add(state);

        if(!StringUtils.isEmpty(email)){
            sqlWhere.append(" AND email like ?");
            paramObjects.add("%" + email + "%");
        }

        if(!StringUtils.isEmpty(firstname)){
            sqlWhere.append(" AND first_name like ?");
            paramObjects.add("%" + firstname + "%");
        }

//        Object[] params = new Object[paramObjects.size()];
//
//        for(int i = 0; i < paramObjects.size(); i++){
//            params[i] = paramObjects.get(i);
//        }

        return jdbcTemplate.queryForObject("SELECT count(id) FROM users " + sqlWhere.toString(), paramObjects.toArray(), Integer.class);
    }

    public List<User> query(String email, String firstname, int state, int limit, int offset){
        StringBuffer sqlWhere = new StringBuffer();
        List<Object> paramObjects = new java.util.ArrayList<Object>();

        sqlWhere.append(" WHERE enabled = ? ");
        paramObjects.add(state);

        if(!StringUtils.isEmpty(email)){
            sqlWhere.append(" AND email like ?");
            paramObjects.add("%" + email + "%");
        }

        if(!StringUtils.isEmpty(firstname)){
            sqlWhere.append(" AND first_name like ?");
            paramObjects.add("%" + firstname + "%");
        }

        sqlWhere.append(" ORDER BY created_at ");
        sqlWhere.append(" LIMIT ?");
        sqlWhere.append(" OFFSET ?");

        paramObjects.add(limit);
        paramObjects.add(offset);

//        Object[] params = new Object[paramObjects.size()];
//
//        for(int i = 0; i < paramObjects.size(); i++){
//            params[i] = paramObjects.get(i);
//        }

        return jdbcTemplate.query("SELECT * FROM users " + sqlWhere.toString(), paramObjects.toArray(), mapper);
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
        sql.append(", state");
        sql.append(", enabled");
        sql.append(", role");
        sql.append(") values(");
        sql.append("?,?,?,?,?,?,?,?,?,?,?)");

        jdbcTemplate.update(sql.toString()
                , user.getId()
                , user.getEmail()
                , user.getPassword()
                , user.getFirstName()
                , user.getLastName()
                , user.getPostcode()
                , user.getAddress()
                , user.getPhone()
                , user.getState()
                , user.getEnabled()
                , user.getRole());

    }

    /**
     * 设置账户状态
     *
     * @param userId
     *          账户ID
     * @param enabled
     *          账户状态值
     * @param state
     *          账户名称
     */
    public void setUserState(String userId, int enabled, String state){
        jdbcTemplate.update("UPDATE users SET enabled = ?, state = ? WHERE id = ?", enabled, state, userId);
    }

    public void delete(String userid){
        jdbcTemplate.update("DELETE FROM users WHERE id = ?", userid);
    }

    public void update(User user){
        StringBuffer sql = new StringBuffer();

        sql.append("UPDATE users SET ");
        sql.append(" first_name = ?, last_name = ?, email = ?");

        if(!StringUtils.isEmpty(user.getPassword())){
            sql.append(", password = ?");
        }

        sql.append(" , phone = ?, address = ?, postcode = ?, role = ?");
        sql.append(" , state = ?, enabled = ?");
        sql.append(" WHERE id = ?");

        if(!StringUtils.isEmpty(user.getPassword())){
            jdbcTemplate.update(sql.toString()
                    , user.getFirstName()
                    , user.getLastName()
                    , user.getEmail()
                    , user.getPassword()
                    , user.getPhone()
                    , user.getAddress()
                    , user.getPostcode()
                    , user.getRole()
                    , user.getState()
                    , user.getEnabled()
                    , user.getId());
        }else {
            jdbcTemplate.update(sql.toString()
                    , user.getFirstName()
                    , user.getLastName()
                    , user.getEmail()
                    , user.getPhone()
                    , user.getAddress()
                    , user.getPostcode()
                    , user.getRole()
                    , user.getState()
                    , user.getEnabled()
                    , user.getId());
        }

    }

    /**
     * 更新用户信息时，检查邮箱是否唯一
     *
     * @param userid
     * @param email
     *
     * @return  true： 不唯一
     *          false: 唯一
     */
    public boolean checkEmail(String userid, String email){
        return jdbcTemplate.queryForObject("SELECT count(id) FROM users WHERE id != ? and email = ?", new Object[]{userid, email}, Integer.class) > 0;
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

//    static class UserVoMapper implements  RowMapper<UserVo>{
//        @Override
//        public UserVo mapRow(ResultSet rs, int rowNum) throws SQLException {
//            UserVo userVo = new UserVo();
//
//            userVo.setUserId(rs.getString("id"));
//            userVo.setUsername(rs.getString("first_name") + rs.getString("last_name"));
//            userVo.setEmail(rs.getString("email"));
//            userVo.setAddress(rs.getString("address"));
//            userVo.setPostcode(rs.getString("postcode"));
//            userVo.setPhone(rs.getString("phone"));
//            userVo.setRole(rs.getString("role"));
//            userVo.setState(rs.getString("state"));
//
//            return userVo;
//        }
//    }


}
