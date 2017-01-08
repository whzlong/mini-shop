package com.cn.chonglin.common.mail.maillogger;

import com.cn.chonglin.common.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 邮件日志服务
 *
 * @author wu
 */
@Service
@Transactional(readOnly = true)
public class MailLogService {
    @Autowired
    private MailLogDao mailLogDao;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save(MailLog t){
        t.setId(IdGenerator.getUuid());
        mailLogDao.insert(t);
    }

    public MailLog findByKey(String id){
        return mailLogDao.findByKey(id);
    }

    public MailLog findByEmail(String email){
        return mailLogDao.findByEmail(email);
    }

}
