package com.triple.o.labs.imageAnalizer.services.impl;

import com.triple.o.labs.imageAnalizer.entities.Notification;
import com.triple.o.labs.imageAnalizer.services.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);


    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail(Notification notification) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(notification.getUser().getEmail());

        msg.setSubject("Medical Case: " + notification.getUser().getName());
        msg.setText(notification.getMessage());

        try {
            javaMailSender.send(msg);
        } catch (MailException e){
            logger.error(String.format("Email to %s could not be sent", notification.getUser().getEmail()));
        }
    }
}
