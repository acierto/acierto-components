package com.aciertoteam.common.utils;

import com.aciertoteam.common.entity.AbstractEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * @author Bogdan Nechyporenko
 */
public class StringToObjectConverter {

    public static Object getValue(AbstractEntity entity, String fieldName, String paramValue) {
        String value = StringUtils.trim(paramValue);
        Class fieldType = getFieldType(entity, fieldName);
        if (BigDecimal.class.isAssignableFrom(fieldType)) {
            return new BigDecimal(value);
        } else if (fieldType == Integer.TYPE) {
            return Integer.valueOf(value);
        } else if (fieldType == Double.TYPE) {
            return Double.valueOf(value);
        } else if (fieldType == Boolean.TYPE) {
            return Boolean.valueOf(value);
        } else if (Currency.class.isAssignableFrom(fieldType)) {
            return Currency.getInstance(value);
        } else if (Enum.class.isAssignableFrom(fieldType)) {
            return EnumCreator.getEnum(fieldType.getName(), paramValue.toUpperCase());
        }
        return value;
    }

    private static Class getFieldType(AbstractEntity entity, String fieldName) {
        return FieldUtils.getField(entity.getClass(), fieldName, true).getType();
    }
}
