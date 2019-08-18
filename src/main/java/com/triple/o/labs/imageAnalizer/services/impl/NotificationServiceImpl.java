package com.triple.o.labs.imageAnalizer.services.impl;

import com.triple.o.labs.imageAnalizer.daos.NotificationDao;
import com.triple.o.labs.imageAnalizer.entities.Notification;
import com.triple.o.labs.imageAnalizer.entities.User;
import com.triple.o.labs.imageAnalizer.services.NotificationService;
import com.triple.o.labs.imageAnalizer.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationDao notificationDao;

    @Autowired
    private UserService userService;

    @Override
    public Notification getNotification(Long id) {
        return notificationDao.findById(id).get();
    }

    @Override
    public Notification updateNotification(Notification notification) {
        notification.setRead(true);
        return notificationDao.save(notification);
    }

    @Override
    public void createNotification(String message) {

        List<User> laboratoryUsers = userService.getLaboratoryUsers();

        for(User labUser : laboratoryUsers) {
            Notification notification = new Notification();
            notification.setMessage(message);
            notification.setUser(labUser);
            notificationDao.save(notification);
        }
    }

    @Override
    public List<Notification> findByUser(User user) {
        return notificationDao.findTop15ByUserOrderByDateCreatedDesc(user);
    }

    @Override
    public List<Notification> getUnreadUserNotifications(User user) {
        return notificationDao.findUnreadUserNotifications(user);
    }

    @Override
    public Long getUnreadUserNotificationsCount(User user) {
        return notificationDao.countByUser(user);
    }
}
