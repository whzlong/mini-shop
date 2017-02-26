package com.cn.chonglin.web.appointment.form;

/**
 * 预约列表
 */
public class AppointmentListForm {
    /**
     * 预约日期
     */
    private String bookDate;

    /**
     * 预约状态
     */
    private String state;

    /**
     * 列表开始页
     */
    private int startPage;

    /**
     * 列表显示当前页
     */
    private int currentPage;

    /**
     * 单页数据量
     */
    private int size;

    public String getBookDate() {
        return bookDate;
    }

    public void setBookDate(String bookDate) {
        this.bookDate = bookDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
