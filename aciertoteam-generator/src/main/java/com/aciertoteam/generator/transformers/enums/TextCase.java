package com.aciertoteam.generator.transformers.enums;

/**
 * @author Bogdan Nechyporenko
 */
public enum TextCase {
    DONT_CHANGE, UPPER_CASE, LOWER_CASE;

    public String apply(String text) {
        switch (this) {
            case UPPER_CASE:
                return text.toUpperCase();
            case LOWER_CASE:
                return text.toLowerCase();
            default:
                return text;
        }
    }
}
