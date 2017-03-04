package com.cn.chonglin.web.order.form;


import com.cn.chonglin.bussiness.order.domain.Order;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OrderForm {
    private String orderId;
    private String payDate;
    @NotEmpty(message = "Please input the state.")
    private String state;
    private String shipAddress;
    private String comment;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getShipAddress() {
        return shipAddress;
    }

    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Order toDomain(){
        Order order = new Order();

        order.setOrderId(Long.valueOf(this.orderId));
        if(!StringUtils.isEmpty(this.payDate)){
            order.setPayDate(LocalDate.parse(this.payDate, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        }
        order.setState(this.state);
        order.setShipAddress(this.shipAddress);
        order.setComment(this.comment);

        return order;
    }
}
