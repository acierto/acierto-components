package com.aciertoteam.mail.entity;

import com.aciertoteam.common.entity.AbstractEntity;
import com.aciertoteam.common.utils.LocaleUtil;
import com.aciertoteam.common.utils.SerializeUtil;
import com.aciertoteam.mail.dto.NotificationDTO;
import com.aciertoteam.mail.enums.NotificationStatus;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * @author Bogdan Nechyporenko
 */
@Entity
public class Notification extends AbstractEntity {

    private static final long serialVersionUID = -8622575437668981919L;

    @Column(name = "SENDER")
    private String from;

    @Column(name = "RECIPIENTS")
    private String to;

    private String comment;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "MAIL_TEMPLATE_ID")
    private MailTemplate mailTemplate;

    @Column(name = "NOTIFICATION_STATUS")
    @Enumerated(value = EnumType.STRING)
    private NotificationStatus status;

    @Column(name = "LOCALE")
    private String locale;

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

    public Notification(NotificationStatus status) {
        this.status = status;
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

    public void setLocale(Locale locale) {
        this.locale = LocaleUtil.toString(locale);
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

    public Long getSid() {
        return 12000 + getId();
    }

    public NotificationDTO createDTO() {
        NotificationDTO dto = new NotificationDTO(LocaleUtil.toLocale(locale));

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
        HashMap<String, Object> readProperties = (HashMap<String, Object>) SerializeUtil.read(getProperties());
        if (readProperties != null) {
            for (Map.Entry<String, Object> entry : readProperties.entrySet()) {
                dto.addProperty(entry.getKey(), (Serializable) entry.getValue());
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void addAttachments(NotificationDTO dto) {
        HashMap<String, Object> readAttachments = (HashMap<String, Object>) SerializeUtil.read(getAttachments());
        if (readAttachments != null) {
            for (Map.Entry<String, Object> entry : readAttachments.entrySet()) {
                dto.addAttachment(entry.getKey(), (Serializable) entry.getValue());
            }
        }
    }

    public void setAttachments(HashMap<String, Object> attachmentMap) {
        this.attachments = SerializeUtil.write(attachmentMap);
    }

    public void setProperties(HashMap<String, Object> parameterMap) {
        this.properties = SerializeUtil.write(parameterMap);
    }

}
