package com.aciertoteam.mail.repositories.impl;

import com.aciertoteam.common.repository.impl.DefaultAbstractRepository;
import com.aciertoteam.mail.entity.MailConfiguration;
import com.aciertoteam.mail.repositories.MailConfigurationRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Bogdan Nechyporenko
 */
@Repository(value = "MailConfigurationRepository")
@Transactional
public class DefaultMailConfigurationRepository extends DefaultAbstractRepository<MailConfiguration> implements MailConfigurationRepository {

    @Override
    public Class getClazz() {
        return MailConfiguration.class;
    }
}
