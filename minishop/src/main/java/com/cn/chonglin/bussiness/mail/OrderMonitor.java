package com.cn.chonglin.bussiness.mail;

import com.cn.chonglin.bussiness.order.domain.Order;
import com.cn.chonglin.common.mail.sender.BaseMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * 生成订单，扣除商品库存数量时，可能会发生死锁
 *
 */
public class OrderMonitor extends BaseMailSender{
    private Order order;

    public OrderMonitor(Order order){
        super(true);

        this.order = order;
    }

    @Override
    protected MimeMessage buildMessage(MimeMessageHelper helper) throws MessagingException {
        helper.setSubject("[Emergency] Item stock deduction failed!");

        String content = buildContent();
        helper.setText(content,true);

        return helper.getMimeMessage();
    }

    private String buildContent() {
        StringBuffer sb = new StringBuffer(500);

        sb.append("<h3>");
        sb.append("When order is created, item stock deduction failed! Please contact the administrator." + " (order number: " + order.getOrderId() + ")");
        sb.append("</h3>");

        return sb.toString();
    }
}
