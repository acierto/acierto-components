package com.aciertoteam.mail.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.aciertoteam.common.entity.AbstractEntity;

/**
 * @author Bogdan Nechyporenko
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"templateName", "validThru"})})
public class MailTemplate extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    private String templateName;

    @Column(nullable = false)
    private String subjectName;

    MailTemplate() {
        //hibernate
    }

    public MailTemplate(String templateName, String subjectName) {
        this.templateName = templateName;
        this.subjectName = subjectName;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
