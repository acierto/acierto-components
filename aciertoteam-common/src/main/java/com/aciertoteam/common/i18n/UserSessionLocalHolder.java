/**
 *
 */
package com.aciertoteam.common.i18n;

import java.util.Locale;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * Utility class that provided static method to access user locale set in the spring context.
 * 
 * @author ishestiporov
 */
public final class UserSessionLocalHolder {

    private static final Logger LOG = Logger.getLogger(UserSessionLocalHolder.class);

    private UserSessionLocalHolder() {
        // restrict instantiation
    }

    public static Locale getUserSessionLocale() {
        WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
        return context != null ? getCurrentUserLocale(context) : getDefaultServerLocale();
    }

    private static Locale getDefaultServerLocale() {
        Locale locale = Locale.getDefault();
        LOG.debug(String.format(
                "Web application context not started or user session bean not found. Using default server locale: %s",
                locale));
        return locale;
    }

    private static Locale getCurrentUserLocale(WebApplicationContext context) {
        try {
            UserSessionLocale userSessionLocale = (UserSessionLocale) context.getBean("userSessionLocale");
            return userSessionLocale.getResolvedLocale();
        } catch (BeansException e) {
            LOG.error(e.getMessage(), e);
            return getDefaultServerLocale();
        }
    }
}
