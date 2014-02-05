package com.aciertoteam.i18n.message;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;
import java.util.Properties;

/**
 * ReloadableResourceBundleMessageSource exposes the merged properties, so they
 * may be used in the frontend Javascript.
 *
 * @author ishestiporov
 */
public class MergedReloadableResourceBundleMessageSource extends ReloadableResourceBundleMessageSource {

    public Properties getAllProperties(Locale locale) {
        return getMergedProperties(locale).getProperties();
    }
}
