package com.aciertoteam.mail.schedulers;

import java.util.Collection;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import com.aciertoteam.mail.DeliveryResult;
import com.aciertoteam.mail.EmailResultCallback;
import com.aciertoteam.mail.beans.DefaultEmailResultCallback;
import com.aciertoteam.mail.dto.NotificationDTO;
import com.aciertoteam.mail.enums.NotificationStatus;
import com.aciertoteam.mail.services.EmailService;
import com.aciertoteam.mail.services.NotificationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Bogdan Nechyporenko
 */
@Component
public class SendNewCreatedNotificationsTask {

    private static final Logger LOG = Logger.getLogger(SendNewCreatedNotificationsTask.class);

    @Autowired
    private EmailService emailService;

    @Autowired
    private NotificationService notificationService;

    @Value("${email.enabled}")
    private boolean activated;

    private Lock lock = new ReentrantLock();

    private Executor executor = Executors.newFixedThreadPool(10);

    @Scheduled(cron = "0 * * * * ?")
    public void run() {
        if (activated) {
            LOG.info("The notification process is started");
            lock.lock();
            try {
                Collection<NotificationDTO> notifications = notificationService.findNotifications(NotificationStatus.CREATED);
                updateNotificationsToPendingStatus(notifications);
                EmailResultCallback callback = new DefaultEmailResultCallback();
                for (NotificationDTO notification : notifications) {
                    sendMessage(notification, callback);
                }
            } finally {
                lock.unlock();
            }
            LOG.info("The notification process is ended");
        }
    }

    private void updateNotificationsToPendingStatus(Collection<NotificationDTO> notifications) {
        notificationService.markAsPending(notifications);
    }

    private void sendMessage(final NotificationDTO notification, final EmailResultCallback callback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    emailService.sendMessage(notification, callback);
                    DeliveryResult deliveryResult = callback.getResult(30, TimeUnit.SECONDS);
                    notificationService.markAsSent(notification.getId(), deliveryResult.isSuccess());
                } catch (Exception e) {
                    LOG.error(e.getMessage(), e);
                    notificationService.markAsSent(notification.getId(), false);
                }
            }
        });
    }
}
