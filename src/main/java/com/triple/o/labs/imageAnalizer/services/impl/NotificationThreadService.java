package com.triple.o.labs.imageAnalizer.services.impl;

import com.triple.o.labs.imageAnalizer.daos.NotificationDao;
import com.triple.o.labs.imageAnalizer.entities.Notification;
import com.triple.o.labs.imageAnalizer.entities.User;
import com.triple.o.labs.imageAnalizer.enums.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationThreadService {

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private NotificationDao notificationDao;

    public void executeNotificationAsynchronously(String message, UserType userType, Long caseId) {

        NotificationService notificationService = applicationContext.getBean(NotificationService.class);
        notificationService.setMessage(message);
        notificationService.setUserType(userType);
        notificationService.setCaseId(caseId);
        taskExecutor.execute(notificationService);
    }

    public void executeNotificationAsynchronously(String message, User user, Long caseId) {

        NotificationService notificationService = applicationContext.getBean(NotificationService.class);
        notificationService.setMessage(message);
        notificationService.setUser(user);
        notificationService.setCaseId(caseId);
        taskExecutor.execute(notificationService);
    }

    public Notification getNotification(Long id) {
        return notificationDao.findById(id).get();
    }

    public Notification updateNotification(Notification notification) {
        notification.setRead(true);
        return notificationDao.save(notification);
    }

    public List<Notification> findByUser(User user) {
        return notificationDao.findTop15ByUserOrderByDateCreatedDesc(user);
    }

    public List<Notification> getUnreadUserNotifications(User user) {
        return notificationDao.findUnreadUserNotifications(user);
    }

    public Long getUnreadUserNotificationsCount(User user) {
        return notificationDao.countByUser(user);
    }
}
