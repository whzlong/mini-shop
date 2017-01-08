package com.cn.chonglin.common.mail.sender;

import com.cn.chonglin.common.mail.maillogger.MailLog;
import com.cn.chonglin.common.mail.maillogger.MailLogService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

/**
 * 邮件发送抽象类
 *
 * @author wu
 */
public abstract class BaseMailSender implements MailSender {
    private static final Logger logger = LoggerFactory.getLogger(BaseMailSender.class);
    protected static final String CHARSET = "UTF-8";

    private final boolean attachment;

    public BaseMailSender(boolean attachment){
        this.attachment = attachment;
    }

    @Override
    public void send(JavaMailSender sender, MailLogService logService, String from, List<String> to){

        MimeMessage m = null;
        try{
            MimeMessageHelper helper = new MimeMessageHelper(sender.createMimeMessage(), attachment, CHARSET);
            helper.setTo(to.toArray(new String[to.size()]));
            helper.setFrom(from);
            m = buildMessage(helper);
            if(m != null) {
                sender.send(m);

                successLog(logService, from, to, subject(m));
            }
        }catch(MessagingException e){
            logger.error("Send mail fail, error is {}", e.getMessage());
            errorLog(logService, from, to, subject(m), e.getMessage());
        }catch (Exception ex){
            logger.error("Send mail error happened.(error:{})", ex.getMessage());
            errorLog(logService, from, to, subject(m), ex.getMessage());
        }
    }

    private String subject(MimeMessage m){
        try{
            return m.getSubject();
        }catch(MessagingException e){
            return "未知错误";
        }
    }

    protected abstract MimeMessage buildMessage(MimeMessageHelper helper)throws MessagingException;

    protected void successLog(MailLogService logService, String from, List<String> to, String subject){
        MailLog t = new MailLog();

        t.setmFrom(from);
        t.setmTo(to);
        t.setSubject(subject);
        t.setState("success");

        logService.save(t);
    }

    private void errorLog(MailLogService logService, String from, List<String> to, String subject, String message){
        MailLog t = new MailLog();

        t.setmFrom(from);
        t.setmTo(to);
        t.setSubject(subject);
        t.setState("error");
        t.setComment(StringUtils.left(message, 400));
        logService.save(t);
    }
}
