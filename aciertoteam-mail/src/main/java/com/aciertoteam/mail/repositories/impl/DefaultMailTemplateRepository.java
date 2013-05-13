package com.aciertoteam.mail.repositories.impl;

import com.aciertoteam.common.repository.impl.DefaultAbstractRepository;
import com.aciertoteam.mail.entity.MailTemplate;
import com.aciertoteam.mail.repositories.MailTemplateRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author Bogdan Nechyporenko
 */
@SuppressWarnings("unchecked")
@Repository(value = "mailTemplateRepository")
@Transactional
public class DefaultMailTemplateRepository extends DefaultAbstractRepository<MailTemplate> implements MailTemplateRepository {

    @Override
    public Class getClazz() {
        return MailTemplate.class;
    }

    @Override
    public MailTemplate findByTemplateName(String templateName) {
        return (MailTemplate) getSession().createQuery("from MailTemplate where templateName = :templateName" +
                " and (validThru is null or validThru > :now)").
                setParameter("templateName", templateName).
                setParameter("now", new Date()).
                uniqueResult();
    }
}
