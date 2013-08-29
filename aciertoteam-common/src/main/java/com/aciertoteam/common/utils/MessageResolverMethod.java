package com.aciertoteam.common.utils;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Locale;

/**
 * @author Bogdan Nechyporenko
 */
public class MessageResolverMethod implements TemplateMethodModel {

    private MessageSource messageSource;
    private Locale locale;

    public MessageResolverMethod(MessageSource messageSource, Locale locale) {
        this.messageSource = messageSource;
        this.locale = locale;
    }

    @Override
    public Object exec(List arguments) throws TemplateModelException {
        if (arguments.size() != 1) {
            throw new TemplateModelException("Wrong number of arguments");
        }
        String code = (String) arguments.get(0);
        if (StringUtils.isBlank(code)) {
            throw new TemplateModelException(String.format("Invalid code value '%s'", code));
        }
        return messageSource.getMessage(code, null, locale);
    }
}
