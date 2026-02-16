package com.superherogame.superhero_backend.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{

    @Value("${email.sender}")
    private String emailSender;
    private final JavaMailSenderImpl mailSender;

    public EmailServiceImpl(JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(String toUser, String subject, String message) {

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(emailSender);
            mimeMessageHelper.setTo(toUser);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(message);

            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }
    }
}
