package com.cn.chonglin.common.mail.maillogger;

import java.sql.Timestamp;
import java.util.List;

/**
 * 邮件发送日志
 *
 * @author wu
 */
public class MailLog {
    private String id;
    private String mFrom;
    private List<String> mTo;
    private String subject;
    private String state;
    private String comment;
    private Timestamp createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getmFrom() {
        return mFrom;
    }

    public void setmFrom(String mFrom) {
        this.mFrom = mFrom;
    }

    public List<String> getmTo() {
        return mTo;
    }

    public void setmTo(List<String> mTo) {
        this.mTo = mTo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}

