package com.triple.o.labs.imageAnalizer.services;

import com.triple.o.labs.imageAnalizer.entities.Notification;

public interface EmailService {
    void sendEmail(Notification notification);
}
