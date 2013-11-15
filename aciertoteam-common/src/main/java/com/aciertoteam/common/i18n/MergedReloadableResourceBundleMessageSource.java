package com.aciertoteam.common.i18n;

import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.Stack;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * ReloadableResourceBundleMessageSource exposes the merged properties, so they
 * may be used in the frontend Javascript.
 * 
 * @author ishestiporov
 */
public class MergedReloadableResourceBundleMessageSource extends ReloadableResourceBundleMessageSource {

    public static final String PROPERTIES_EXTENSION = ".properties";
    private PathMatchingResourcePatternResolver resourceLoader = new PathMatchingResourcePatternResolver();

    public Properties getAllProperties(Locale locale) {
        return getMergedProperties(locale).getProperties();
    }
}
