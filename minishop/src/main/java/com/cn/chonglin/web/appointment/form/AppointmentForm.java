package com.cn.chonglin.web.appointment.form;

import com.cn.chonglin.bussiness.appointment.domain.Appointment;
import org.hibernate.validator.constraints.NotEmpty;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * 预约表单
 *
 * @author wu
 */
public class AppointmentForm {
    @NotEmpty(message = "Please select the date!")
    private String bookDate;
    @NotEmpty(message = "Please select the time!")
    private String bookTime;
    private String itemId;

    public String getBookDate() {
        return bookDate;
    }

    public void setBookDate(String bookDate) {
        this.bookDate = bookDate;
    }

    public String getBookTime() {
        return bookTime;
    }

    public void setBookTime(String bookTime) {
        this.bookTime = bookTime;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Appointment toDomain(){
        Appointment appointment = new Appointment();

        appointment.setItemId(this.itemId);

        appointment.setBookDate(LocalDate.parse(this.bookDate, DateTimeFormatter.ofPattern("yyyy/MM/dd")));
        appointment.setBookTime(LocalTime.parse(this.bookTime, DateTimeFormatter.ofPattern("HH:mm")));

        return appointment;
    }
}
