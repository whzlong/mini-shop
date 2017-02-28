package com.cn.chonglin.bussiness.appointment.service;

import com.cn.chonglin.bussiness.appointment.dao.AppointmentDao;
import com.cn.chonglin.bussiness.appointment.domain.Appointment;
import com.cn.chonglin.bussiness.appointment.vo.AppointmentVo;
import com.cn.chonglin.bussiness.base.dao.UserDao;
import com.cn.chonglin.bussiness.base.domain.User;
import com.cn.chonglin.bussiness.mail.AppointmentConfimSender;
import com.cn.chonglin.common.IdGenerator;
import com.cn.chonglin.common.ListPage;
import com.cn.chonglin.common.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 预约Service
 */
@Service
public class AppointmentService {

    @Autowired
    private AppointmentDao appointmentDao;

    @Autowired
    private MailService mailService;

    @Autowired
    private UserDao userDao;

    public ListPage<AppointmentVo> query(String bookDate, String state, int limit, int offset){

        int count = appointmentDao.countAppointments(bookDate, state);

        List<AppointmentVo> appointmentVos = appointmentDao.queryForList(bookDate, state, limit, offset*limit);

        return new ListPage<>(count, appointmentVos);
    }

    public void add(Appointment appointment){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userDao.findByEmail(email);

        appointment.setId(IdGenerator.getUuid());
        appointment.setUserId(user.getId());
        appointment.setState("Pending");

        appointmentDao.insert(appointment);
    }

    public void edit(Appointment updateItem){

        Appointment appointment = appointmentDao.findByKey(updateItem.getId());

        appointment.setBookDate(updateItem.getBookDate());
        appointment.setBookTime(updateItem.getBookTime());
        appointment.setState(updateItem.getState());
        appointment.setComment(updateItem.getComment());

        appointmentDao.update(appointment);
    }

    public void confirmWithCustomer(Appointment updateItem){
        User user = userDao.findByKey(appointmentDao.findByKey(updateItem.getId()).getUserId());

        edit(updateItem);

        //发送帐户验证邮件
        List<String> mailList = new ArrayList<>();
        mailList.add(user.getEmail());
        mailService.asynSend(new AppointmentConfimSender(updateItem), mailList);
    }
}
