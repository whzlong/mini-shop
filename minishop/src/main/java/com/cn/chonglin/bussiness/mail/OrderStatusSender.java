package com.cn.chonglin.bussiness.mail;

import com.cn.chonglin.bussiness.order.domain.Order;
import com.cn.chonglin.common.mail.sender.BaseMailSender;
import com.cn.chonglin.constants.DropdownListContants;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * 给客户发送订单状态
 *
 */
public class OrderStatusSender extends BaseMailSender {
    private Order order;

    public OrderStatusSender(Order order){
        super(true);
        this.order = order;
    }


    @Override
    protected MimeMessage buildMessage(MimeMessageHelper helper) throws MessagingException {
        helper.setSubject("The order status");

        String content = buildContent();
        helper.setText(content,true);

        return helper.getMimeMessage();
    }

    private String buildContent() {
        StringBuffer sb = new StringBuffer(300);

        sb.append("<br>");

        if(DropdownListContants.ORDER_STATE_REPAIRING.equals(order.getState())){
            sb.append("Your device has been repairing.");
        }else if(DropdownListContants.ORDER_STATE_OVER.equals(order.getState())){
            sb.append("Your order has been completed.");
        }else{
            sb.append("Your order status is " + order.getState());
        }

        sb.append("<br>");
        sb.append("<br>");
        sb.append("Order No: " + order.getOrderId());


        return sb.toString();
    }

}
