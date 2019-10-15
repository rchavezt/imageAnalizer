package com.triple.o.labs.imageAnalizer.services;

import com.triple.o.labs.imageAnalizer.entities.Notification;
import com.triple.o.labs.imageAnalizer.entities.User;
import com.triple.o.labs.imageAnalizer.enums.UserType;

import java.util.List;

public interface NotificationService {
    Notification getNotification(Long id);
    Notification updateNotification(Notification notification);
    void createNotification(String message, UserType userType);
    void createNotification(String message, User user);
    List<Notification> findByUser(User user);
    List<Notification> getUnreadUserNotifications(User user);
    Long getUnreadUserNotificationsCount(User user);
}
