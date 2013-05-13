package com.aciertoteam.mail.entity;

import com.aciertoteam.common.utils.SerializeUtil;
import com.aciertoteam.mail.dto.NotificationDTO;
import com.aciertoteam.mail.enums.NotificationStatus;
import org.springframework.util.StringUtils;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Bogdan Nechyporenko
 */
@Entity
public class Notification extends AbstractEntity {

    private static final long serialVersionUID = -8622575437668981919L;

    @Column(name = "sender")
    private String from;

    @Column(name = "recipients")
    private String to;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn
    private MailTemplate mailTemplate;

    @Column
    private String comment;

    @Column(name = "notification_status")
    @Enumerated(value = EnumType.STRING)
    private NotificationStatus status;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    @Column(length = 1024 * 1024)
    private byte[] properties;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    @Column(length = 1024 * 1024)
    private byte[] attachments;

    public Notification() {
        status = NotificationStatus.CREATED;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public MailTemplate getMailTemplate() {
        return mailTemplate;
    }

    public void setMailTemplate(MailTemplate mailTemplate) {
        this.mailTemplate = mailTemplate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    public byte[] getProperties() {
        return properties;
    }

    public void setProperties(byte[] properties) {
        this.properties = properties;
    }

    public byte[] getAttachments() {
        return attachments;
    }

    public void setAttachments(byte[] attachments) {
        this.attachments = attachments;
    }

    public static List<NotificationDTO> createDTOList(Collection<Notification> notifications) {
        List<NotificationDTO> result = new LinkedList<NotificationDTO>();
        for (Notification notification : notifications) {
            result.add(notification.createDTO());
        }
        return result;
    }

    public NotificationDTO createDTO() {
        NotificationDTO dto = new NotificationDTO();

        dto.setId(getId());
        dto.setComment(getComment());
        dto.setFrom(getFrom() == null ? null : getFrom());
        dto.setTemplateName(getMailTemplate().getTemplateName());
        dto.setSubjectName(getMailTemplate().getSubjectName());
        dto.setStatus(getStatus());
        dto.addTo(new LinkedList<String>(StringUtils.commaDelimitedListToSet(to)));

        addProperties(dto);
        addAttachments(dto);

        return dto;
    }

    @SuppressWarnings("unchecked")
    private void addProperties(NotificationDTO dto) {
        HashMap<String, Object> properties = (HashMap<String, Object>) SerializeUtil.read(getProperties());

        if (properties != null) {
            for (Map.Entry<String, Object> entry : properties.entrySet()) {
                dto.addProperty(entry.getKey(), (Serializable) entry.getValue());
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void addAttachments(NotificationDTO dto) {
        HashMap<String, Object> attachments = (HashMap<String, Object>) SerializeUtil.read(getAttachments());

        if (attachments != null) {
            for (Map.Entry<String, Object> entry : attachments.entrySet()) {
                dto.addAttachment(entry.getKey(), (Serializable) entry.getValue());
            }
        }
    }

}
