package com.cn.chonglin.bussiness.appointment.service;

import com.cn.chonglin.bussiness.appointment.dao.AppointmentDao;
import com.cn.chonglin.bussiness.appointment.domain.Appointment;
import com.cn.chonglin.bussiness.appointment.vo.AppointmentVo;
import com.cn.chonglin.bussiness.appointment.vo.SimpleAppointmentVo;
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

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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

        //对于已经为确认状态的预约信息不生成订单信息
        if(!DropdownListContants.APPOINTMENT_STATE_CONFIRMED.equals(appointment.getState())){
            //如果状态为"已确认"，则生成订单信息
            if(DropdownListContants.APPOINTMENT_STATE_CONFIRMED.equals(updateItem.getState())){
                orderService.createOrderFromAppointment(appointment);
            }
        }


        //更新预约信息
        appointment.setBookDate(updateItem.getBookDate());
        appointment.setBookTime(updateItem.getBookTime());
        appointment.setState(updateItem.getState());
        appointment.setComment(updateItem.getComment());

        appointmentDao.update(appointment);
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

    /**
     * 通过期间查询预约信息
     *
     */
    public List<SimpleAppointmentVo> queryAppointments(String period){
        LocalDate localDate = LocalDate.now();

        switch(period){
            case DropdownListContants.PERIOD_HALF_YEAR_VALUE:
                localDate = localDate.minus(6, ChronoUnit.MONTHS);
                break;
            case DropdownListContants.PERIOD_YEAR_VALUE:
                localDate = localDate.minus(12, ChronoUnit.MONTHS);
                break;
            default:
                localDate = localDate.minus(1, ChronoUnit.MONTHS);
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userDao.findByEmail(username);

        List<SimpleAppointmentVo> ff = appointmentDao.queryByPeriod(user.getId(), localDate);

        return ff;
    }

    /**
     * 客户端更新预约日期和时间
     *
     * @param id
     *          预约ID
     * @param bookDate
     *          预约日期
     * @param bookTime
     *          预约时间
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateAppointmentDatetime(String id, String bookDate, String bookTime){
        Appointment appointment = appointmentDao.findByKey(id);

        appointment.setBookDate(LocalDate.parse(bookDate, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        appointment.setBookTime(LocalTime.parse(bookTime, DateTimeFormatter.ofPattern("HH:mm")));

        appointmentDao.update(appointment);
    }
}
