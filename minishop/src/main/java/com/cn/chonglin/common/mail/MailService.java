package com.cn.chonglin.common.mail;

import com.cn.chonglin.common.mail.maillogger.MailLogService;
import com.cn.chonglin.common.mail.sender.MailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 邮件日志处理服务
 *
 * @author wu
 */
@Service
public class MailService {
    private static final Logger logger = LoggerFactory.getLogger(MailService.class);

    @Autowired
    private MailLogService logService;

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private MailProperties properties;

    private ExecutorService executor =  Executors.newFixedThreadPool(2);

    public void asynSend(MailSender mailSender, List<String> to){
        executor.execute(() -> {
            try{
                mailSender.send(sender, logService, properties.getUsername(), to);
            }catch(Exception e){
                logger.debug("Send mail error happened.(error:{})", e.getMessage());
            }
        });
    }

    public void send(MailSender mailSender, List<String> to){
        mailSender.send(sender, logService, properties.getUsername(), to);
    }
}
