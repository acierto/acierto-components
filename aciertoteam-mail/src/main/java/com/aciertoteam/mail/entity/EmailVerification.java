package com.aciertoteam.mail.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.aciertoteam.common.entity.AbstractEntity;
import com.aciertoteam.mail.enums.RequestStatus;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "NOTIFICATION_ID")
    @Cascade({ CascadeType.SAVE_UPDATE, CascadeType.MERGE })
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

    public RequestStatus updateVerificationStatus(String token) {
        if (isExpired()) {
            requestStatus = RequestStatus.EXPIRED;
        } else if (isValidToken(token)) {
            requestStatus = RequestStatus.SUCCESS;
        } else {
            requestStatus = RequestStatus.FAILED;
        }
        return requestStatus;
    }

    private boolean isValidToken(String token) {
        return getToken().equals(token);
    }

    private boolean isExpired() {
        Date validThru = getValidThru();
        return validThru != null && validThru.before(new Date());
    }
}
