package com.aciertoteam.mail.repositories;

import com.aciertoteam.common.repository.AbstractRepository;
import com.aciertoteam.mail.entity.Notification;
import com.aciertoteam.mail.enums.NotificationStatus;

import java.util.List;

/**
 * @author Bogdan Nechyporenko
 */
public interface NotificationRepository extends AbstractRepository<Notification> {

    /**
     * Returns the notifications which have the listed statuses.
     *
     * @param statuses
     * @return
     */
    List<Notification> findNotificationByStatuses(List<NotificationStatus> statuses);
}
