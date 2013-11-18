package com.aciertoteam.mail.repositories.impl;

import com.aciertoteam.common.repository.impl.DefaultAbstractRepository;
import com.aciertoteam.mail.entity.Notification;
import com.aciertoteam.mail.enums.NotificationStatus;
import com.aciertoteam.mail.repositories.NotificationRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Bogdan Nechyporenko
 */
@SuppressWarnings("unchecked")
@Repository(value = "notificationRepository")
@Transactional
public class DefaultNotificationRepository extends DefaultAbstractRepository<Notification> implements
        NotificationRepository {

    @Override
    public List<Notification> findNotificationByStatuses(List<NotificationStatus> statuses) {
        return getSession().createQuery("from Notification where status in (:statuses)")
                .setParameterList("statuses", statuses).list();
    }
}
