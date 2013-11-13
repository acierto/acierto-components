package com.aciertoteam.common.mvc.converter;

import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

/**
 * Default converter for date objects.
 * @author ishestiporov
 */
public class DateConverter implements Converter<String, Date> {

    private static final Logger LOG = LoggerFactory.getLogger(DateConverter.class);

    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    private static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern(DEFAULT_DATE_FORMAT);

    private String customDateFormat;

    @Override
    public Date convert(String source) {
        try {
            if (StringUtils.isBlank(customDateFormat)) {
                return FORMATTER.parseDateTime(source).toDate();
            }
            return DateTimeFormat.forPattern(customDateFormat).parseDateTime(source).toDate();
        } catch (Exception e) {
            LOG.error(String.format("Cannot convert date %s tdue to error %s", source, e.getMessage()), e);
            throw new IllegalArgumentException(e);
        }
    }

    public void setCustomDateFormat(String customDateFormat) {
        this.customDateFormat = customDateFormat;
    }
}
