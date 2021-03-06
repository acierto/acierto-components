package com.aciertoteam.mail.enums;

/**
 * @author Bogdan Nechyporenko
 */
public enum NotificationStatus {
    CREATED, PENDING, SUCCESSFULLY_SENT, FAILED_SENT;

    public boolean isCreated() {
        return CREATED.equals(this);
    }
}
