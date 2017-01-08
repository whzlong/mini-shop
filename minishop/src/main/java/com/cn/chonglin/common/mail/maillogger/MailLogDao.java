package com.cn.chonglin.common.mail.maillogger;

import com.cn.chonglin.common.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 邮件日志数据访问Dao
 *
 * @author wu
 */
@Repository
public class MailLogDao {
    private final RowMapper<MailLog> mapper = new MailLogDao.MailLogMapper();
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(@Qualifier("loggerDatasource")DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(MailLog t){
        jdbcTemplate.update("INSERT INTO mail_logs (id, m_from, m_to, state, comment) VALUES (?, ?, ?, ?, ?)",
                t.getId(), t.getmFrom(), DataUtils.join(t.getmTo()), t.getState(), t.getComment());
    }

    public MailLog findByKey(String id){
        return jdbcTemplate.queryForObject("SELECT * FROM mail_logs WHERE id = ?", new Object[]{id}, mapper);
    }

    public MailLog findByEmail(String email){
        try{
            return jdbcTemplate.queryForObject("SELECT * FROM mail_logs WHERE m_to = ?", new Object[]{email}, mapper);
        }catch (EmptyResultDataAccessException ex){
            return null;
        }
    }

    static class MailLogMapper implements RowMapper<MailLog> {

        @Override
        public MailLog mapRow(ResultSet rs, int rowNum) throws SQLException {
            MailLog t = new MailLog();

            t.setId(rs.getString("id"));
            t.setmFrom(rs.getString("m_from"));
            t.setmTo(DataUtils.toList(rs.getString("m_to")));
            t.setComment(rs.getString("comment"));
            t.setState(rs.getString("state"));
            t.setCreatedAt(rs.getTimestamp("created_at"));

            return t;
        }
    }


}
