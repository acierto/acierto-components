package com.aciertoteam.mail.repositories;

import com.aciertoteam.common.repository.AbstractRepository;
import com.aciertoteam.mail.entity.MailTemplate;

/**
 * @author Bogdan Nechyporenko
 */
public interface MailTemplateRepository extends AbstractRepository<MailTemplate> {

    /**
     * Returns mail template by specified template name
     *
     * @param templateName
     * @return
     */
    MailTemplate findByTemplateName(String templateName);
}
