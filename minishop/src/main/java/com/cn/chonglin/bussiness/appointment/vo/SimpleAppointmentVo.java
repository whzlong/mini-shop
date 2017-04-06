package com.cn.chonglin.bussiness.appointment.vo;

/**
 * 客户端预约列表
 */
public class SimpleAppointmentVo {
    private String id;
    private String bookDate;
    private String bookTime;
    private String itemName;
    private String itemListImage;
    private String modelName;

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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemListImage() {
        return itemListImage;
    }

    public void setItemListImage(String itemListImage) {
        this.itemListImage = itemListImage;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
}
