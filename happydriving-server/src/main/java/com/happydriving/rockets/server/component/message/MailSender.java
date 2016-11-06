package com.happydriving.rockets.server.component.message;

import com.happydriving.rockets.server.common.BusinessException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mazhiqiang
 */
@Component
public class MailSender {

    public static final String DEFAULT_TYPE = "text/html;charset=UTF-8";

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    public void sendMail(String subject, String message) throws BusinessException {
//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//        try {
//            mimeMessage.setSubject(subject);
//            mimeMessage.setContent(message, DEFAULT_TYPE);
//            javaMailSender.send(mimeMessage);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }


        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMultipart mimeMultipart = new MimeMultipart();
            mimeMessage.setSubject(subject);
            BodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(message, DEFAULT_TYPE);
            mimeMultipart.addBodyPart(mimeBodyPart);

            mimeMessage.setContent(mimeMultipart);
            mimeMessage.setFrom(new InternetAddress("inform@ejiapei.com"));
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress("info_xiamen@ejiapei.com"));

//            if (!mailInfo.getMailToList().isEmpty()) {
//                List<InternetAddress> toAddressList = new ArrayList<InternetAddress>();
//                for (String mailTo : mailInfo.getMailToList()) {
//                    toAddressList.add(new InternetAddress(mailTo));
//                }
//                mimeMessage.setRecipients(Message.RecipientType.TO,
//                        toAddressList.toArray(new InternetAddress[toAddressList.size()]));
//            }
//
//            if (!mailInfo.getMailCcList().isEmpty()) {
//                List<InternetAddress> ccAddressList = new ArrayList<InternetAddress>();
//                for (String mailCc : mailInfo.getMailCcList()) {
//                    ccAddressList.add(new InternetAddress(mailCc));
//                }
//                mimeMessage.setRecipients(Message.RecipientType.CC,
//                        ccAddressList.toArray(new InternetAddress[ccAddressList.size()]));
//            }
//
//            if (!mailInfo.getMailBccList().isEmpty()) {
//                List<InternetAddress> bccAddressList = new ArrayList<InternetAddress>();
//                for (String mailBcc : mailInfo.getMailBccList()) {
//                    bccAddressList.add(new InternetAddress(mailBcc));
//                }
//                mimeMessage.setRecipients(Message.RecipientType.BCC,
//                        bccAddressList.toArray(new InternetAddress[bccAddressList.size()]));
//            }
//
//            for (String attachFileName : mailInfo.getAttachments()) {
//                BodyPart attachmentBodyPart = new MimeBodyPart();
//                FileDataSource fileDataSource = new FileDataSource(attachFileName);
//                attachmentBodyPart.setDataHandler(new DataHandler(fileDataSource));
//                attachmentBodyPart.setFileName(fileDataSource.getName());
//                mimeMultipart.addBodyPart(attachmentBodyPart);
//            }

            mimeMessage.saveChanges();
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new BusinessException(e);
        }
    }

    public void sendTemplateMail(MailConfiguration mailConfiguration) throws BusinessException {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMultipart mimeMultipart = new MimeMultipart();
            mimeMessage.setSubject(mailConfiguration.getSubject());
            BodyPart mimeBodyPart = new MimeBodyPart();

            Template template = freeMarkerConfigurer.getConfiguration().getTemplate(mailConfiguration.getTemplate());
            Map<String, Object> data = new HashMap<>();
            data.put("mailInfo", mailConfiguration.getMailObject());

            String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, data);

            mimeBodyPart.setContent(content, DEFAULT_TYPE);
            mimeMultipart.addBodyPart(mimeBodyPart);

            mimeMessage.setContent(mimeMultipart);
            mimeMessage.setFrom(new InternetAddress("inform@ejiapei.com"));

            if (CollectionUtils.isNotEmpty(mailConfiguration.getMailToReceipients())) {
                for (String mailToReceipient : mailConfiguration.getMailToReceipients()) {
                    mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(mailToReceipient));
                }
            }

            if (CollectionUtils.isNotEmpty(mailConfiguration.getMailCcReceipients())) {
                for (String mailCcReceipient : mailConfiguration.getMailCcReceipients()) {
                    mimeMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(mailCcReceipient));
                }
            }
            mimeMessage.saveChanges();
            javaMailSender.send(mimeMessage);
        } catch (MessagingException | IOException | TemplateException e) {
            throw new BusinessException(e);
        }
    }

//    public void sendTemplateMailWithAttachment(String subject, Object mailInfo) throws BusinessException {
//        try {
//            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//
//            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
//            mimeMessageHelper.addAttachment("mediator.xls", new File("/Users/mazhiqiang/Downloads/mediatee.xls"));
//            mimeMessageHelper.setFrom("mazhiqiang@miaozhen.com");
//            mimeMessageHelper.setTo("mazhiqiang@miaozhen.com");
//            mimeMessageHelper.setSubject(subject);
//
//            Template template = freeMarkerConfigurer.getConfiguration().getTemplate("mailcontent.ftl");
//            Map<String, Object> data = new HashMap<>();
//            data.put("mailInfo", mailInfo);
//
//            String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, data);
//            mimeMessageHelper.setText(content, true);
//            javaMailSender.send(mimeMessage);
//        } catch (MessagingException | IOException | TemplateException e) {
//            throw new BusinessException(e);
//        }
//    }
}
