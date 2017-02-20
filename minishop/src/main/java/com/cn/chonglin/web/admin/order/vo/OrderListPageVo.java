package com.cn.chonglin.web.admin.order.vo;


public class OrderListPageVo {
    private String orderId;

    private String orderDateFrom;

    private String orderDateTo;

    private String state;

    private int currentPage;

    private int beginPage;

    private int endPage;

    private int leftPage;

    private int totalPage;

    private int listSize;

    private int pageSize;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDateFrom() {
        return orderDateFrom;
    }

    public void setOrderDateFrom(String orderDateFrom) {
        this.orderDateFrom = orderDateFrom;
    }

    public String getOrderDateTo() {
        return orderDateTo;
    }

    public void setOrderDateTo(String orderDateTo) {
        this.orderDateTo = orderDateTo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getBeginPage() {
        return beginPage;
    }

    public void setBeginPage(int beginPage) {
        this.beginPage = beginPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public int getLeftPage() {
        return leftPage;
    }

    public void setLeftPage(int leftPage) {
        this.leftPage = leftPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getListSize() {
        return listSize;
    }

    public void setListSize(int listSize) {
        this.listSize = listSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}

