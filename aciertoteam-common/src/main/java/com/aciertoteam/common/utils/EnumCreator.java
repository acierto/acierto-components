package com.aciertoteam.common.utils;

/**
 * @author Bogdan Nechyporenko
 */
public class EnumCreator {

    @SuppressWarnings("unchecked")
    public static Enum<?> getEnum(String enumName, String value) {
        try {
            Class<Enum> enumClass = (Class<Enum>) Class.forName(enumName);
            return Enum.valueOf(enumClass, value);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
