package com.aciertoteam.mail.services.impl;

import com.aciertoteam.mail.dto.NotificationDTO;
import com.aciertoteam.mail.entity.EmailVerification;
import com.aciertoteam.mail.entity.Notification;
import com.aciertoteam.mail.enums.NotificationStatus;
import com.aciertoteam.mail.enums.RequestStatus;
import com.aciertoteam.mail.repositories.EmailVerificationRepository;
import com.aciertoteam.mail.repositories.NotificationRepository;
import com.aciertoteam.mail.services.MailConfigurationService;
import com.aciertoteam.mail.services.NotificationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Bogdan Nechyporenko
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class DefaultNotificationService implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private EmailVerificationRepository emailVerificationRepository;

    @Autowired
    private MailConfigurationService configurationService;

    @Override
    public EmailVerification createVerificationToken(String email, String token) {
        return emailVerificationRepository.createVerificationToken(email, token);
    }

    @Override
    public Collection<NotificationDTO> findNotifications(NotificationStatus status) {
        return Notification.createDTOList(notificationRepository.findCollectionByField("status", status));
    }

    public Map<String, Long> loadNotifications(Collection<Notification> notifications) {
        notificationRepository.saveAll(notifications);
        return createNotificationMap(notifications);
    }

    private Map<String, Long> createNotificationMap(Collection<Notification> notifications) {
        Map<String, Long> notificationMap = new HashMap<String, Long>();
        for (Notification notification : notifications) {
            notificationMap.put(getEmail(notification), notification.getId());
        }
        return notificationMap;
    }

    private String getEmail(Notification notification) {
        String email = notification.getFrom();
        if (StringUtils.isNotBlank(email)) {
            return configurationService.getConfig("mail.user").getValue();
        }
        return email;
    }

    public void markAsSent(long notificationId, boolean successfully) {
        Notification notification = notificationRepository.get(notificationId);
        notification.setStatus(successfully ? NotificationStatus.SUCCESSFULLY_SENT : NotificationStatus.FAILED_SENT);
        updateEmailVerificationStatus(notification);
    }

    @Override
    public void markAsPending(Collection<NotificationDTO> notifications) {
        for (NotificationDTO n : notifications) {
            Notification notification = notificationRepository.get(n.getId());
            notification.setStatus(NotificationStatus.PENDING);
            notificationRepository.saveOrUpdate(notification);
        }
    }

    private void updateEmailVerificationStatus(Notification notification) {
        EmailVerification emailVerification = emailVerificationRepository.findByNotificationId(notification.getId());
        if (emailVerification != null) {
            emailVerification.setRequestStatus(RequestStatus.PENDING);
            emailVerificationRepository.saveOrUpdate(emailVerification);
        }
    }
}
