package com.cn.chonglin.web.client.item.form;

import org.hibernate.validator.constraints.NotEmpty;

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
}
