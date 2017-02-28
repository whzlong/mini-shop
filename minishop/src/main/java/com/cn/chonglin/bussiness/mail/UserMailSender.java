package com.cn.chonglin.bussiness.mail;

import com.cn.chonglin.bussiness.base.domain.Verification;
import com.cn.chonglin.common.mail.sender.BaseMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * 用户邮件发送
 *
 * @author wu
 */
public class UserMailSender extends BaseMailSender {
    private Verification verification;

    public UserMailSender(Verification verification){
        super(true);
        this.verification = verification;
    }

    @Override
    protected MimeMessage buildMessage(MimeMessageHelper helper) throws MessagingException {
        helper.setSubject("Thanks for your registration!");

        String content = buildContent();
        helper.setText(content,true);

        return helper.getMimeMessage();
    }

    private String buildContent() {
        StringBuffer sb = new StringBuffer(300);
        String confirmUrl = "http://localhost:8080/client/confirm/" + verification.getVerificationCode();
        sb.append("Thank you very much for your registration. <br>");
        sb.append("Please click the link below to validate your account.<br>");
        sb.append("<a href='" + confirmUrl+"'>" + confirmUrl + "</a>");

        return sb.toString();
    }
}
