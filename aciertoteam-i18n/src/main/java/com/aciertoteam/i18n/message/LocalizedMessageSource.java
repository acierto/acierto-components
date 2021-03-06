/**
 *
 */
package com.aciertoteam.i18n.message;

import com.aciertoteam.i18n.UserSessionLocale;
import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;
import java.util.Properties;

/**
 * Utility class for resolving messaging from the property files. Wraps
 * {@link MessageSource} from Spring and is using the locale to resolve
 * messages. Works together with
 * {@link com.aciertoteam.i18n.interceptor.AciertoteamLocaleChangeInterceptor}
 * which sets here locale changes.
 *
 * @author ishestiporov
 */
public class LocalizedMessageSource {

    private static final Logger LOGGER = Logger.getLogger(LocalizedMessageSource.class);
    private MessageSource messageSource;
    private UserSessionLocale userSessionLocale;

    public String getMessage(String code) {
        return getMessage(getLocale(), code);
    }

    public String getMessage(String code, Object... args) {
        return getMessage(getLocale(), code, args);
    }

    /**
     * This method is keeping with public accessor as batch process doesn't have
     * session scope bean and at this case we need to transfer Locale value
     * directly.
     */
    public String getMessage(Locale locale, String code, Object... args) {
        try {
            return messageSource.getMessage(code, args, locale);
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

    public String getPriceAsString(Currency currency, BigDecimal price) {
        return String.format("%s %s", currency.getSymbol(getLocale()), price.toPlainString());
    }

    public Properties getAllProperties() {
        return ((MergedReloadableResourceBundleMessageSource) messageSource).getAllProperties(getLocale());
    }
}
