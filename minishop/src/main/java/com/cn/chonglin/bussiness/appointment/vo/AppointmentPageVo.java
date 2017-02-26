package com.cn.chonglin.bussiness.appointment.vo;


import java.util.List;

public class AppointmentPageVo {
    private List<AppointmentVo> appointmentVos;
    private int startPage;
    private int currentPage;
    private int totalPage;

    public List<AppointmentVo> getAppointmentVos() {
        return appointmentVos;
    }

    public void setAppointmentVos(List<AppointmentVo> appointmentVos) {
        this.appointmentVos = appointmentVos;
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

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
