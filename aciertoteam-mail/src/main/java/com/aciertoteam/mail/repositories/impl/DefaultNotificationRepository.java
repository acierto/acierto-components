package com.aciertoteam.mail.repositories.impl;

import com.aciertoteam.common.repository.impl.DefaultAbstractRepository;
import com.aciertoteam.mail.entity.Notification;
import com.aciertoteam.mail.repositories.NotificationRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Bogdan Nechyporenko
 */
@SuppressWarnings("unchecked")
@Repository(value = "notificationRepository")
@Transactional
public class DefaultNotificationRepository extends DefaultAbstractRepository<Notification> implements NotificationRepository {

}
