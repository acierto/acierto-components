package com.aciertoteam.mail.enums;

/**
 * @author Bogdan Nechyporenko
 */
public enum RequestStatus {

    NOT_FOUND, REQUESTED, PENDING, SUCCESS, FAILED, REJECTED, EXPIRED;

    public boolean isFailed() {
        return FAILED.equals(this) || REJECTED.equals(this) || EXPIRED.equals(this);
    }

    public boolean isSuccess() {
        return SUCCESS.equals(this);
    }
}
