package com.cn.chonglin.bussiness.base.service;

import com.cn.chonglin.bussiness.base.dao.UserDao;
import com.cn.chonglin.bussiness.base.domain.User;
import com.cn.chonglin.bussiness.mail.UserMailSender;
import com.cn.chonglin.common.AppException;
import com.cn.chonglin.common.IdGenerator;
import com.cn.chonglin.common.mail.MailService;
import com.cn.chonglin.common.mail.maillogger.MailLog;
import com.cn.chonglin.common.mail.maillogger.MailLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户服务
 *
 * @author wu
 */
@Service
@Transactional(readOnly = true)
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private static final int USER_ENABLED_ZERO = 0;
    private static final int USER_ENABLED_ONE = 1;

    private static final String ROLE_DEFAULT = "USER";

    @Autowired
    private UserDao userDao;

    @Autowired
    private MailService mailService;

    @Autowired
    private MailLogService mailLogService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional(propagation = Propagation.REQUIRED)
    public User save(User user){
        checkInput(user);

        try{
            user.setId(IdGenerator.getUuid());
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setEnabled(USER_ENABLED_ZERO);
            user.setRole(ROLE_DEFAULT);

            userDao.insert(user);

            //发送帐户验证邮件
            List<String> mailList = new ArrayList<>();
            mailList.add(user.getEmail());
            mailService.asynSend(new UserMailSender(user), mailList);
        }catch(Exception ex){
            logger.error("Email sending is failed.(customerId:{}, email:{})", user.getId(), user.getEmail());
        }

        return userDao.findByKey(user.getId());
    }

    /**
     * 验证帐户
     * @param id
     *        验证码
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void confirmRegister(String id){
        User user = userDao.findByKey(id);

        if(user == null){
            //是否为有效链接
            throw new AppException("The link was invalid.");
        }

        //帐户是否已经被激活
        if(USER_ENABLED_ONE == user.getEnabled()){
            throw new AppException("The account has been validated.");
        }

        MailLog mailLog = mailLogService.findByEmail(user.getEmail());

        //是否为有效链接
        if(mailLog == null){
            throw new AppException("The link was invalid.");
        }

        //是否过期（超过24小时）
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime createdAt = mailLog.getCreatedAt().toLocalDateTime();

        createdAt = createdAt.plusHours(24);

        if(createdAt.isBefore(localDateTime)){
            throw new AppException("The link was expired, please register your account once again.");
        }

        userDao.updateEnabled(user.getId(), USER_ENABLED_ONE);

    }

    private void checkInput(User user){
        User userInfo = userDao.findByEmail(user.getEmail());

        if(userInfo != null){
            throw new AppException("The email have been registered, please use another email.");
        }
    }

    /**
     * 查找用户信息
     * @param username
     *        电邮
     * @return
     */
    public User getUserByUsername(String username){
        return userDao.findByEmail(username);
    }

    public User findByKey(String id){
        return userDao.findByKey(id);
    }

}
