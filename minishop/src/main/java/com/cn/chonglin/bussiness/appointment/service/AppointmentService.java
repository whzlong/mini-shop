package com.cn.chonglin.bussiness.appointment.service;

import com.cn.chonglin.bussiness.appointment.dao.AppointmentDao;
import com.cn.chonglin.bussiness.appointment.domain.Appointment;
import com.cn.chonglin.bussiness.appointment.vo.AppointmentVo;
import com.cn.chonglin.bussiness.base.dao.UserDao;
import com.cn.chonglin.bussiness.base.domain.User;
import com.cn.chonglin.common.IdGenerator;
import com.cn.chonglin.common.ListPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 预约Service
 */
@Service
public class AppointmentService {

    @Autowired
    private AppointmentDao appointmentDao;

    @Autowired
    private UserDao userDao;

    public ListPage<AppointmentVo> query(String bookDate, String state, int limit, int offset){

        int count = appointmentDao.countAppointments(bookDate, state);

        List<AppointmentVo> appointmentVos = appointmentDao.queryForList(bookDate, state, limit, offset*limit);

        return new ListPage<>(count, appointmentVos);
    }

    public void add(Appointment appointment){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userDao.findByEmail(username);

        appointment.setId(IdGenerator.getUuid());
        appointment.setUserId(user.getId());
        appointment.setState("Pending");

        appointmentDao.insert(appointment);
    }
}
