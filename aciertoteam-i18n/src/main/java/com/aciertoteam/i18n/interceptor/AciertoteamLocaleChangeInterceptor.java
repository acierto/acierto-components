package com.aciertoteam.i18n.interceptor;

import com.aciertoteam.i18n.UserSessionLocale;
import org.springframework.util.StringUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * @author Bogdan Nechyporenko
 */
public class AciertoteamLocaleChangeInterceptor extends LocaleChangeInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws ServletException {

        String newLocale = request.getParameter(getParamName());
        if (newLocale != null) {
            Locale locale = StringUtils.parseLocaleString(newLocale);
            setUserLocale(locale);
        }
        return super.preHandle(request, response, handler);
    }

    private void setUserLocale(Locale locale) {
        UserSessionLocale userSessionLocale = getUserSessionLocale();
        if (userSessionLocale != null) {
            userSessionLocale.setLocale(locale);
        }
    }

    private UserSessionLocale getUserSessionLocale() {
        return (UserSessionLocale) ContextLoader.getCurrentWebApplicationContext().getBean("userSessionLocale");
    }
}
