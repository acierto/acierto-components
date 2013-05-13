package com.aciertoteam.mail.repositories;

import com.aciertoteam.common.repository.AbstractRepository;
import com.aciertoteam.mail.entity.EmailVerification;

/**
 * @author Bogdan Nechyporenko
 */
public interface EmailVerificationRepository extends AbstractRepository<EmailVerification> {

    /**
     * Creates a token which will be provided to the end user for email verification
     *
     * @param email
     * @param token
     */
    EmailVerification createVerificationToken(String email, String token);

    /**
     * Finds the state of email verification based on person id.
     *
     * @return
     */
    EmailVerification getByEmail(String email);

    /**
     * Returns the email verification based on the notification sent to the end user.
     *
     * @param notificationId
     * @return
     */
    EmailVerification findByNotificationId(Long notificationId);
}
