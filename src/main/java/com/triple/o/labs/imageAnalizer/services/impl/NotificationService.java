package com.triple.o.labs.imageAnalizer.services.impl;

import com.triple.o.labs.imageAnalizer.daos.NotificationDao;
import com.triple.o.labs.imageAnalizer.entities.Notification;
import com.triple.o.labs.imageAnalizer.entities.User;
import com.triple.o.labs.imageAnalizer.enums.UserType;
import com.triple.o.labs.imageAnalizer.services.EmailService;
import com.triple.o.labs.imageAnalizer.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("prototype")
public class NotificationService implements Runnable  {

    @Autowired
    private NotificationDao notificationDao;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    private String message;
    private UserType userType;
    private User user;
    private Long caseId;

    @Override
    public void run() {
        createNotification();
    }

    public void createNotification() {

        if (userType != null) {
            List<User> users = userService.getUsersbyType(userType);

            for (User user : users) {
                Notification notification = new Notification();
                notification.setMessage(message);
                notification.setUser(user);
                notification.setCaseId(caseId);
                notificationDao.save(notification);

                emailService.sendEmail(notification);
            }
        } else {
            Notification notification = new Notification();
            notification.setMessage(message);
            notification.setUser(user);
            notification.setCaseId(caseId);
            notificationDao.save(notification);

            emailService.sendEmail(notification);
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getCaseId() {
        return caseId;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }
}
