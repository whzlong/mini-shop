package com.cn.chonglin.bussiness.appointment.service;

import com.cn.chonglin.bussiness.appointment.dao.AppointmentDao;
import com.cn.chonglin.bussiness.appointment.domain.Appointment;
import com.cn.chonglin.bussiness.appointment.vo.AppointmentVo;
import com.cn.chonglin.bussiness.base.dao.UserDao;
import com.cn.chonglin.bussiness.base.domain.User;
import com.cn.chonglin.bussiness.mail.AppointmentConfimSender;
import com.cn.chonglin.bussiness.order.service.OrderService;
import com.cn.chonglin.common.IdGenerator;
import com.cn.chonglin.common.ListPage;
import com.cn.chonglin.common.mail.MailService;
import com.cn.chonglin.constants.DropdownListContants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 预约Service
 */
@Service
@Transactional(readOnly = true)
public class AppointmentService {

    @Autowired
    private AppointmentDao appointmentDao;

    @Autowired
    private MailService mailService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private OrderService orderService;

    public ListPage<AppointmentVo> query(String bookDate, String state, int limit, int page){

        int count = appointmentDao.countAppointments(bookDate, state);

        List<AppointmentVo> appointmentVos = appointmentDao.queryForList(bookDate, state, limit, page*limit);

        return new ListPage<>(count, appointmentVos);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void add(Appointment appointment){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userDao.findByEmail(email);

        appointment.setId(IdGenerator.getUuid());
        appointment.setUserId(user.getId());
        appointment.setState(DropdownListContants.APPOINTMENT_STATE_PENGING);

        appointmentDao.insert(appointment);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void edit(Appointment updateItem){

        Appointment appointment = appointmentDao.findByKey(updateItem.getId());

        appointment.setBookDate(updateItem.getBookDate());
        appointment.setBookTime(updateItem.getBookTime());
        appointment.setState(updateItem.getState());
        appointment.setComment(updateItem.getComment());

        appointmentDao.update(appointment);

        //如果状态为"已确认"，则生成订单信息
        if(DropdownListContants.APPOINTMENT_STATE_CONFIRMED.equals(updateItem.getState())){
            orderService.createOrderFromAppointment(appointment);
        }

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(String appointmentId){
        appointmentDao.delete(appointmentId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void confirmWithCustomer(Appointment updateItem){
        User user = userDao.findByKey(appointmentDao.findByKey(updateItem.getId()).getUserId());

        edit(updateItem);

        //发送帐户验证邮件
        List<String> mailList = new ArrayList<>();
        mailList.add(user.getEmail());
        mailService.asynSend(new AppointmentConfimSender(updateItem), mailList);
    }
}
