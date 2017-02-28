package com.cn.chonglin.bussiness.base.service;

import com.cn.chonglin.bussiness.base.dao.UserDao;
import com.cn.chonglin.bussiness.base.dao.VerificationDao;
import com.cn.chonglin.bussiness.base.domain.User;
import com.cn.chonglin.bussiness.base.domain.Verification;
import com.cn.chonglin.bussiness.mail.UserMailSender;
import com.cn.chonglin.common.AppException;
import com.cn.chonglin.common.IdGenerator;
import com.cn.chonglin.common.mail.MailService;
import com.cn.chonglin.common.mail.maillogger.MailLogService;
import com.cn.chonglin.constants.DropdownListContants;
import com.cn.chonglin.constants.RoleContants;
import org.apache.commons.lang3.StringUtils;
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

    @Autowired
    private UserDao userDao;

    @Autowired
    private MailService mailService;

    @Autowired
    private MailLogService mailLogService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private VerificationDao verificationDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public User save(User user){
        checkInput(user);

        try{
            user.setId(IdGenerator.getUuid());
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setEnabled(DropdownListContants.USER_STATE_PENDING_VALUE);
            user.setState(DropdownListContants.USER_STATE_PENDING);
            user.setRole(RoleContants.ROLE_GENERAL);

            userDao.insert(user);

            //验证信息
            Verification verification = new Verification();
            verification.setVerificationCode(IdGenerator.getUuid());
            verification.setUserId(user.getId());

            verificationDao.insert(verification);


            //发送帐户验证邮件
            List<String> mailList = new ArrayList<>();
            mailList.add(user.getEmail());
            mailService.asynSend(new UserMailSender(verification), mailList);
        }catch(Exception ex){
            logger.error("Email sending is failed.(customerId:{}, email:{})", user.getId(), user.getEmail());
        }

        return userDao.findByKey(user.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void update(User user){
        if(userDao.checkEmail(user.getId(), user.getEmail())){
            throw new AppException("The email have been registered, please use another email.");
        }

        if(!StringUtils.isEmpty(user.getPassword())){
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }

        userDao.update(user);

    }

    /**
     * 验证帐户
     * @param id
     *        验证码
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void confirmRegister(String id){
        Verification verification = verificationDao.findByKey(id);

        if(verification == null){
            //是否为有效链接
            throw new AppException("The link was invalid.");
        }

        User user = userDao.findByKey(verification.getUserId());

        //帐户是否已经被激活
        if(DropdownListContants.USER_STATE_ACTIVE_VALUE == user.getEnabled()){
            throw new AppException("The account has been validated.");
        }

        //是否过期（超过24小时）
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime createdAt = verification.getCreated_at();

        createdAt = createdAt.plusHours(24);

        if(createdAt.isBefore(localDateTime)){
            throw new AppException("The link was expired, please register your account once again.");
        }

        userDao.setUserState(user.getId(), DropdownListContants.USER_STATE_ACTIVE_VALUE, DropdownListContants.USER_STATE_ACTIVE);
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
        return userDao.findValidUser(username);
    }

    public User findByKey(String id){
        return userDao.findByKey(id);
    }

    public int count(String email, String firstname, int state){
        return userDao.count(email, firstname, state);
    }

    public List<User> query(String email, String firstname, int state, int limit, int offset){
        return userDao.query(email, firstname, state, limit, offset);
    }

    /**
     * 屏蔽用户
     *
     * @param userId
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void disableUser(String userId){
        userDao.setUserState(userId, DropdownListContants.USER_STATE_SHIELDED_VALUE, DropdownListContants.USER_STATE_SHIELDED);
    }

    /**
     * 删除用户
     * @param userid
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteUser(String userid){
        userDao.delete(userid);
    }

}
