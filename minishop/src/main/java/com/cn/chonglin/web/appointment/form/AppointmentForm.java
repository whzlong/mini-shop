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
    private String id;
    @NotEmpty(message = "Please select the date!")
    private String bookDate;
    @NotEmpty(message = "Please select the time!")
    private String bookTime;
    private String itemId;

    private String state;

    private String comment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Appointment toDomain(){
        Appointment appointment = new Appointment();

        appointment.setId(this.id);
        appointment.setItemId(this.itemId);

        appointment.setBookDate(LocalDate.parse(this.bookDate, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        appointment.setBookTime(LocalTime.parse(this.bookTime, DateTimeFormatter.ofPattern("HH:mm")));

        appointment.setState(this.state);

        appointment.setComment(this.comment);
        return appointment;
    }
}
