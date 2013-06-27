/**
 *
 */
package com.aciertoteam.common.i18n;

import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Locale;

/**
 * Utility class for resolving messaging from the property files. Wraps
 * {@link MessageSource} from Spring and is using the locale to resolve
 * messages. Works together with {@link AciertoteamLocaleChangeInterceptor} which sets
 * here changes locale.
 *
 * @author ishestiporov
 */
public class LocalizedMessageSource extends RequestContextHolder {

    private static final Logger LOGGER = Logger.getLogger(LocalizedMessageSource.class);

    private MessageSource messageSource;

    private UserSessionLocale userSessionLocale;

    public String getMessage(String code) {
        try {
            return messageSource.getMessage(code, null, getLocale());
        } catch (NoSuchMessageException e) {
            LOGGER.error(e.getMessage(), e);
            return "";
        }
    }

    public String getMessage(String code, Object... args) {
        try {
            return messageSource.getMessage(code, args, getLocale());
        } catch (NoSuchMessageException e) {
            LOGGER.error(e.getMessage(), e);
            return "";
        }
    }

    public Locale getLocale() {
        return userSessionLocale != null ? userSessionLocale.getResolvedLocale() : Locale.getDefault();
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public void setUserSessionLocale(UserSessionLocale userSessionLocale) {
        this.userSessionLocale = userSessionLocale;
    }

}
