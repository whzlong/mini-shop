package com.cn.chonglin.bussiness.base.dao;

import com.cn.chonglin.bussiness.base.domain.Verification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class VerificationDao {
    private final RowMapper<Verification> verificationRowMapper = new VerificationMapper();

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(Verification verification){
        jdbcTemplate.update("INSERT INTO verifications(verification_code, user_id) VALUES(?,?)"
                            , verification.getVerificationCode()
                            , verification.getUserId());
    }

    public Verification findByKey(String id){
        return jdbcTemplate.queryForObject("SELECT * FROM verifications WHERE verification_code = ?", new Object[]{id}, verificationRowMapper);
    }

    static class VerificationMapper implements RowMapper<Verification>{
        @Override
        public Verification mapRow(ResultSet rs, int rowNum) throws SQLException {
            Verification verification = new Verification();

            verification.setVerificationCode(rs.getString("verification_code"));
            verification.setUserId(rs.getString("user_id"));
            verification.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());

            return verification;
        }
    }
}
