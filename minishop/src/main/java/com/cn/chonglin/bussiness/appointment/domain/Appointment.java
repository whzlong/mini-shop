package com.cn.chonglin.bussiness.appointment.domain;


import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {
    private String id;

    private String userId;

    private String itemId;

    private LocalDate bookDate;

    private LocalTime bookTime;

    private String state;

    private String updatedAt;

    private String created_at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public LocalDate getBookDate() {
        return bookDate;
    }

    public void setBookDate(LocalDate bookDate) {
        this.bookDate = bookDate;
    }

    public LocalTime getBookTime() {
        return bookTime;
    }

    public void setBookTime(LocalTime bookTime) {
        this.bookTime = bookTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
