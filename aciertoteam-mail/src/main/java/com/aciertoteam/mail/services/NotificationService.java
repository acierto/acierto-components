package com.aciertoteam.mail.services;

import com.aciertoteam.mail.dto.NotificationDTO;
import com.aciertoteam.mail.entity.DecisionRequest;
import com.aciertoteam.mail.entity.EmailVerification;
import com.aciertoteam.mail.entity.Notification;
import com.aciertoteam.mail.enums.NotificationStatus;

import java.util.Collection;
import java.util.Map;

/**
 * @author Bogdan Nechyporenko
 */
public interface NotificationService {

    /**
     * Should be called after saving of notification request which to be pended till user
     * will take an decision about reject/approval status of it.
     */
    void notifyUserAboutRegisteredDecisionRequest(DecisionRequest decisionRequest);

    /**
     * After changing the state of request to final the status the user can be notified by this method
     * to know what the result has been taken.
     */
    void notifyUserAboutTakenDecisionByRequest(DecisionRequest decisionRequest, String description);

    /**
     * Creates a token which will be provided to the end user for email verification
     *
     * @param email
     * @param token
     */
    EmailVerification createVerificationToken(String email, String token);

    /**
     * Returns the found notification of specified status for the full period of the system.
     *
     * @param status
     * @return
     */
    Collection<NotificationDTO> findNotifications(NotificationStatus status);

    /**
     * @param notifications
     * @return
     */
    Map<String, Long> loadNotifications(Collection<Notification> notifications);

    /**
     * @param notificationId
     * @param successfully
     */
    void markAsSent(long notificationId, boolean successfully);

    /**
     * Change that status for transferred notifications to PENDING
     *
     * @param notifications
     */
    void markAsPending(Collection<NotificationDTO> notifications);
}
