package com.cn.chonglin.bussiness.base.dao;

import com.cn.chonglin.bussiness.base.domain.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 系统设置Dao
 */
@Repository
public class SettingDao {
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Setting> settingRowMapper = new SettingMapper();

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Setting queryForObject(){
        return jdbcTemplate.queryForObject("SELECT * FROM setting", new Object[]{}, settingRowMapper);
    }

    static class SettingMapper implements RowMapper<Setting>{
        @Override
        public Setting mapRow(ResultSet rs, int rowNum) throws SQLException {
            Setting setting = new Setting();

            setting.setCurrency(rs.getString("currency"));
            setting.setEmail(rs.getString("email"));

            return setting;
        }
    }
}
