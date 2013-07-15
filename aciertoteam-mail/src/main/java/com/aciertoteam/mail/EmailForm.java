package com.aciertoteam.mail;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.util.StringUtils;

/**
 * @author Bogdan Nechyporenko
 */
public class EmailForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private String from;

    private List<String> to;

    private String bodyText;

    private String subject;

    private List<ImageAttachment> imageAttachments = new LinkedList<ImageAttachment>();

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBodyText() {
        return bodyText;
    }

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public List<String> getTo() {
        return to;
    }

    public String getRecipients() {
        return StringUtils.collectionToCommaDelimitedString(to);
    }

    public void setTo(List<String> to) {
        this.to = to;
    }

    public void setTo(String to) {
        List<String> set = new LinkedList<String>();
        set.add(to);
        this.to = set;
    }

    public void addImageAttachments(Map<String, Object> notificationAttachments) {
        if (notificationAttachments != null) {
            for (Map.Entry<String, Object> entry : notificationAttachments.entrySet()) {
                InputStream inputStream = getClass().getResourceAsStream((String) entry.getValue());
                imageAttachments.add(new ImageAttachment(entry.getKey(), inputStream, "image/png"));
            }
        }
    }

    public List<ImageAttachment> getImageAttachments() {
        return Collections.unmodifiableList(imageAttachments);
    }

    /**
     * Contains an attachment for the letter
     */
    public static class ImageAttachment implements Serializable {

        private final InputStream inputStream;

        private final String mimeType;

        private final String imageTemplateId;

        public ImageAttachment(String imageTemplateId, InputStream inputStream, String mimeType) {
            this.inputStream = inputStream;
            this.mimeType = mimeType;
            this.imageTemplateId = imageTemplateId;
        }

        public InputStream getInputStream() {
            return inputStream;
        }

        public String getMimeType() {
            return mimeType;
        }

        public String getImageTemplateId() {
            return imageTemplateId;
        }
    }
}
