package com.aciertoteam.common.utils;

import java.util.Locale;

/**
 * @author Bogdan Nechyporenko
 */
public class LocaleUtil {

    public static String toString(Locale locale) {
        return String.format("%s_%s", locale.getLanguage(), locale.getCountry());
    }

    public static Locale toLocale(String text) {
        String[] localeValues = text.split("_");
        return new Locale(localeValues[0], localeValues[1]);
    }
}
