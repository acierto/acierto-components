package com.aciertoteam.common.utils;

import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.inject.Singleton;

/**
 * @author Bogdan Nechyporenko
 */
@Singleton
public final class PropertyUtil {

    private PropertyUtil() {
        //singleton
    }

    public static List<String> readFile(String path) {
        return readFile(path, Locale.getDefault());
    }

    public static List<String> readFile(String path, Locale locale) {
        List<String> countries = new LinkedList<String>();
        Enumeration<String> keyEnumerator = ResourceBundle.getBundle(path, locale).getKeys();
        while (keyEnumerator.hasMoreElements()) {
            countries.add(keyEnumerator.nextElement());
        }
        return countries;
    }
}
