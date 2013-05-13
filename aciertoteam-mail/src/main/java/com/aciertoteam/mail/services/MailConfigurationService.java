package com.aciertoteam.mail.services;

import com.aciertoteam.mail.entity.MailConfiguration;

/**
 * @author Bogdan Nechyporenko
 */
public interface MailConfigurationService {

    /**
     * Adds a new configuration based upon the input parameters, default key value will be the same with current one
     *
     * @param key
     * @param value
     */
    void addNewConfig(String key, String value);

    /**
     * Adds a new configuration based upon the input parameters
     *
     * @param key
     * @param value
     * @param defaultValue
     */
    void addNewConfig(String key, String value, String defaultValue);

    /**
     * Retrieves a configuration for a specific key. If the entry will not be found, null will be returned.
     *
     * @param key
     * @return
     */
    MailConfiguration getConfig(String key);

    /**
     * First try to find an existed value and change the value from old to new one, if the entry hadn't been found,
     * creates new one with defined value.
     *
     * @param key
     * @param value
     */
    void changeValue(String key, String value);
}
