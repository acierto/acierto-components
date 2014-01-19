package com.aciertoteam.common.utils;

import com.aciertoteam.common.entity.AbstractEntity;
import com.aciertoteam.common.service.EntityService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Bogdan Nechyporenko
 */
@Component
public class StringToObjectConverter {

    @Autowired
    private EntityService entityService;

    public Object getValue(AbstractEntity entity, String fieldName, String[] paramValues) {
        if (paramValues.length == 1) {
            return getSingleValue(entity, fieldName, paramValues[0], false);
        } else {
            Class collectionFieldType = getFieldType(entity, fieldName);
            if (List.class.isAssignableFrom(collectionFieldType)) {
                List<Object> list = new ArrayList<Object>();
                for (String paramValue: paramValues) {
                    list.add(getSingleValue(entity, fieldName, paramValue, true));
                }
                return list;
            } else if (Set.class.isAssignableFrom(collectionFieldType)) {
                Set<Object> set = new HashSet<Object>();
                for (String paramValue: paramValues) {
                    set.add(getSingleValue(entity, fieldName, paramValue, true));
                }
                return set;
            }
        }
        return null;
    }

    private Object getSingleValue(AbstractEntity entity, String fieldName, String paramValue, boolean elementFromCollection) {
        String value = StringUtils.trim(paramValue);
        Class fieldType = getFieldType(entity, fieldName, elementFromCollection);
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
            return EnumCreator.getEnum(fieldType.getName(), value.toUpperCase());
        } else if (AbstractEntity.class.isAssignableFrom(fieldType)) {
            return entityService.findById(fieldType, Long.valueOf(value));
        }
        return value;
    }

    private static Class getFieldType(AbstractEntity entity, String fieldName, boolean elementFromCollection) {
        if (elementFromCollection) {
            return getFieldTypeForElementInCollections(entity, fieldName);
        }
        return getFieldType(entity, fieldName);
    }

    private static Class getFieldType(AbstractEntity entity, String fieldName) {
        return FieldUtils.getField(entity.getClass(), fieldName, true).getType();
    }

    private static Class getFieldTypeForElementInCollections(AbstractEntity entity, String fieldName) {
        Field field = FieldUtils.getField(entity.getClass(), fieldName, true);
        ParameterizedType stringListType = (ParameterizedType) field.getGenericType();
        return (Class<?>) stringListType.getActualTypeArguments()[0];
    }
}
