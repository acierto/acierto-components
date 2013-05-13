package com.aciertoteam.mail.services.impl;

import com.aciertoteam.mail.entity.MailConfiguration;
import com.aciertoteam.mail.repositories.MailConfigurationRepository;
import com.aciertoteam.mail.services.MailConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Bogdan Nechyporenko
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class DefaultMailConfigurationService implements MailConfigurationService {

    @Autowired
    private MailConfigurationRepository configurationRepository;

    public void addNewConfig(String key, String value) {
        configurationRepository.save(new MailConfiguration(key, value, value));
    }

    public void addNewConfig(String key, String value, String defaultValue) {
        configurationRepository.save(new MailConfiguration(key, value, defaultValue));
    }

    public MailConfiguration getConfig(String key) {
        return configurationRepository.findByField("key", key);
    }

    public void changeValue(String key, String value) {
        MailConfiguration config = configurationRepository.findByField("key", key);
        config.setValue(value);
        configurationRepository.saveOrUpdate(config);
    }
}
