package com.aciertoteam.common.utils;

import javax.inject.Singleton;
import java.util.*;

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
