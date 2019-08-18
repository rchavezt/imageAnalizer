package com.triple.o.labs.imageAnalizer.daos;

import com.triple.o.labs.imageAnalizer.entities.Notification;
import com.triple.o.labs.imageAnalizer.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationDao extends CrudRepository<Notification, Long> {
    List<Notification> findTop15ByUserOrderByDateCreatedDesc(User user);

    @Query("from Notification where isRead = 0 and user = :user order by dateCreated desc")
    List<Notification> findUnreadUserNotifications(@Param("user") User user);

    @Query("select count(n) from Notification n where isRead = 0 and user = :user")
    Long countByUser(@Param("user") User user);
}
