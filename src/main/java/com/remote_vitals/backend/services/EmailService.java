package com.remote_vitals.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Value("${spring.mail.password}")
    private String senderPassword;


    public EmailService(@Autowired JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public String sendEmail(String recipientEmail, String subject, String messageText, List<String> attachmentPaths) {
        try {
            // Create a MimeMessagePreparator that will prepare the MimeMessage for sending
            MimeMessagePreparator messagePreparator = mimeMessage -> {
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
                messageHelper.setFrom(senderEmail); // sender email
                messageHelper.setTo(recipientEmail); // recipient email
                messageHelper.setSubject(subject);  // email subject
                messageHelper.setText(messageText); // email body

                // Adding attachments
                if (attachmentPaths != null) {
                    for (String path : attachmentPaths) {
                        File file = new File(path);
                        if (file.exists() && file.isFile()) {
                            messageHelper.addAttachment(file.getName(), file);
                        }
                    }
                }
            };
            // Send email
            mailSender.send(messagePreparator);
            return "Email Sent Successfully";
        }
        catch(Exception e){
            return "Email not sent due to some error";
        }
    }


}
