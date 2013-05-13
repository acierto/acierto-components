package com.aciertoteam.mail.entity;

import com.aciertoteam.mail.enums.RequestStatus;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author Bogdan Nechyporenko
 */
@Entity
public class EmailVerification extends AbstractEntity {

    @Column
    private String token;

    @Column
    @Enumerated(value = EnumType.STRING)
    private RequestStatus requestStatus;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn
    private Notification notification;

    @Column
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public boolean isVerified() {
        return requestStatus.isSuccess();
    }
}
