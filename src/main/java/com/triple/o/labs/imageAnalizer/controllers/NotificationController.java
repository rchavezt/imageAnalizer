package com.triple.o.labs.imageAnalizer.controllers;

import com.triple.o.labs.imageAnalizer.config.UserPrincipal;
import com.triple.o.labs.imageAnalizer.config.security.CurrentUser;
import com.triple.o.labs.imageAnalizer.dtos.responses.NotificationResponseDto;
import com.triple.o.labs.imageAnalizer.entities.Notification;
import com.triple.o.labs.imageAnalizer.entities.User;
import com.triple.o.labs.imageAnalizer.exceptions.BadRequestException;
import com.triple.o.labs.imageAnalizer.services.NotificationService;
import com.triple.o.labs.imageAnalizer.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/notification", headers = "Authorization")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
    public List<NotificationResponseDto> getUserNotifications(@CurrentUser UserPrincipal userPrincipal){
        User user = userService.getUser(userPrincipal.getId());
        List<Notification> notifications = notificationService.findByUser(user);
        List<NotificationResponseDto> responseDtos = new ArrayList<>();

        for(Notification notification : notifications){
            NotificationResponseDto notificationResponseDto = new NotificationResponseDto();
            BeanUtils.copyProperties(notification, notificationResponseDto);
            responseDtos.add(notificationResponseDto);
        }

        return responseDtos;
    }

    @RequestMapping(value = "/all/unread/count", method = RequestMethod.GET, produces = "application/json")
    public Long getUnreadUserNotificationsCount(@CurrentUser UserPrincipal userPrincipal){
        User user = userService.getUser(userPrincipal.getId());
        return notificationService.getUnreadUserNotificationsCount(user);
    }

    @RequestMapping(value = "/all/unread", method = RequestMethod.GET, produces = "application/json")
    public List<NotificationResponseDto> getUnreadUserNotifications(@CurrentUser UserPrincipal userPrincipal){
        List<Notification> notifications = notificationService.getUnreadUserNotifications(userService.getUser(userPrincipal.getId()));
        List<NotificationResponseDto> notificationResponseDtos = new ArrayList<>();

        for (Notification notification : notifications){
            NotificationResponseDto notificationResponseDto = new NotificationResponseDto();
            BeanUtils.copyProperties(notification, notificationResponseDto);
            notificationResponseDtos.add(notificationResponseDto);
        }

        return notificationResponseDtos;
    }

    @RequestMapping(value = "/read/{id}", method = RequestMethod.PUT, produces = "application/json")
    public NotificationResponseDto readNotification(@CurrentUser UserPrincipal userPrincipal, @PathVariable Long id){
        Notification notification = notificationService.getNotification(id);

        if (notification.getUser() != userService.getUser(userPrincipal.getId())) {
            throw new BadRequestException("This notification is not assigned to the logged user, please try again");
        }

        notification = notificationService.updateNotification(notification);
        NotificationResponseDto notificationResponseDto = new NotificationResponseDto();
        BeanUtils.copyProperties(notification, notificationResponseDto);
        return notificationResponseDto;
    }
}
