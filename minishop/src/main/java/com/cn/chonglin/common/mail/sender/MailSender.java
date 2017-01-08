package com.cn.chonglin.common.mail.sender;

import com.cn.chonglin.common.mail.maillogger.MailLogService;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;

/**
 * 邮件发送接口
 *
 * @author wu
 */
public interface MailSender {
    void send(JavaMailSender sender, MailLogService logService, String from, List<String> to);
}
