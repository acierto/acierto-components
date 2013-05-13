package com.aciertoteam.mail.services;

import com.aciertoteam.mail.EmailResultCallback;
import com.aciertoteam.mail.dto.NotificationDTO;

/**
 * @author Bogdan Nechyporenko
 */
public interface EmailService {

    /**
     * Sends a messages by using FreeMarker Template
     *
     * @param notification
     */
    void sendMessage(final NotificationDTO notification, EmailResultCallback callback);
}
