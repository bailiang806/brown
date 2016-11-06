package com.happydriving.rockets.server.component.message;

import java.util.List;

/**
 * 发送邮件的基本设置，例如使用的模版，发送人、接收人的基本信息等
 * @author mazhiqiang
 */
public class MailConfiguration {

    /**
     * 邮件使用的freemarker模版，模版需要放置在 /WEB-INF/mailtemplate目录下
     */
    private String template;
    /**
     * 邮件接收列表
     */
    private List<String> mailToReceipients;
    /**
     * 邮件抄送列表
     */
    private List<String> mailCcReceipients;

    /**
     * 邮件主题
     */
    private String subject;
    /**
     * 用于向template中注入freemarker的对象
     */
    private Object mailObject;

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public List<String> getMailToReceipients() {
        return mailToReceipients;
    }

    public void setMailToReceipients(List<String> mailToReceipients) {
        this.mailToReceipients = mailToReceipients;
    }

    public List<String> getMailCcReceipients() {
        return mailCcReceipients;
    }

    public void setMailCcReceipients(List<String> mailCcReceipients) {
        this.mailCcReceipients = mailCcReceipients;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Object getMailObject() {
        return mailObject;
    }

    public void setMailObject(Object mailObject) {
        this.mailObject = mailObject;
    }
}
