package com.youland.markting.config.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Deprecated
@Component
public class EmailSender {

    private final JavaMailSender mailSender;

    private final String FROM_EMAIL_ADDRESS = "borrow@youland.com";

    @Autowired
    public EmailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // for sending simple email message
    public void sendMailMessage(String to, String subject, String content) {
        mailSender.send(mimeMessage -> {
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(FROM_EMAIL_ADDRESS);
            helper.addTo(to);
            helper.setSubject(subject);
            helper.setText(content, false);
        });
    }


    public void sendMailMessage(String to, String subject, String htmlContent, InputStreamSource attachment, String attachmentName, String cc) {
        mailSender.send(mimeMessage -> {
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(FROM_EMAIL_ADDRESS);
            helper.addTo(to);
            helper.addCc(cc);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            helper.addAttachment(attachmentName, attachment);
        });
    }

    public void sendMailMessage(MimeMessageHelper mimeMessageHelper) {
        mailSender.send(mimeMessageHelper.getMimeMessage());
    }

    public MimeMessageHelper createMimeMessageHelper() throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper =
                new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setFrom(FROM_EMAIL_ADDRESS);
        return helper;
    }

    public MimeMessageHelper createMimeMessageHelper(String to, String subject) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper =
                new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setFrom(FROM_EMAIL_ADDRESS);
        helper.addTo(to);
        helper.setSubject(subject);
        return helper;
    }


}
