package com.cn.chonglin.bussiness.mail;

import com.cn.chonglin.bussiness.appointment.domain.Appointment;
import com.cn.chonglin.common.mail.sender.BaseMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * 预约确认
 *
 */
public class AppointmentConfimSender extends BaseMailSender{
    private Appointment appointment;

    public AppointmentConfimSender(Appointment appointment) {
        super(true);
        this.appointment = appointment;
    }

    @Override
    protected MimeMessage buildMessage(MimeMessageHelper helper) throws MessagingException {
        helper.setSubject("Please check your appointment from PhoneRepair site!");

        String content = buildContent();
        helper.setText(content,true);

        return helper.getMimeMessage();
    }

    private String buildContent() {
        StringBuffer sb = new StringBuffer(500);

        sb.append("<h3>");
        sb.append(appointment.getComment());
        sb.append("</h3>");

        return sb.toString();
    }
}
