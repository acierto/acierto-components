package com.aciertoteam.common.utils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * Class that replaces equals with just a single line. Can be used only on small
 * lists of objects as it uses reflection which slows down the performance. This
 * is null safe and type safe implementation which is in accordance with the
 * equals contract.
 * 
 * <p>
 * A typical invocation for this method would look like:
 * </p>
 * 
 * <pre>
 * &#064;Override
 * public final boolean equals(Object obj) {
 *     return ContractEqualsBuilder.isEquals(this, obj, &quot;name&quot;);
 * }
 * </pre>
 * 
 * @author ishestiporov
 */
public final class ContractEqualsBuilder {

    private ContractEqualsBuilder() {
        // restrict instantiation
    }

    /**
     * @param thisObject current object to compare
     * @param otherObject other object to compare
     * @param fields object fields to include in comparison
     * @return true if objects are equal
     */
    public static boolean isEquals(Object thisObject, Object otherObject, String... fields) {
        return isEquals(thisObject, otherObject, false, false, fields);
    }

    public static boolean isEqualsWithAllLocalFields(Object thisObject, Object otherObject) {
        List<String> fields = new ArrayList<String>();
        for (Field field : thisObject.getClass().getDeclaredFields()) {
            fields.add(field.getName());
        }
        return isEquals(thisObject, otherObject, false, false, fields.toArray(new String[fields.size()]));
    }

    public static boolean isEqualsWithSuper(Object thisObject, Object otherObject, boolean superEqualsResult,
            String... fields) {
        return isEquals(thisObject, otherObject, true, superEqualsResult, fields);
    }

    private static boolean isEquals(Object thisObject, Object otherObject, boolean appendSuper,
            boolean superEqualsResult, String[] fields) {
        if (otherObject == null) {
            return false;
        }
        if (otherObject == thisObject) {
            return true;
        }
        if (otherObject.getClass() != thisObject.getClass()) {
            return false;
        }
        return areFieldEquals(thisObject, otherObject, appendSuper, superEqualsResult, fields);
    }

    private static boolean areFieldEquals(Object thisObject, Object otherObject, boolean appendSuper,
            boolean superEqualsResult, String[] fields) {
        final EqualsBuilder equalsBuilder = constructEqualsBuilder(appendSuper, superEqualsResult);
        for (String field : fields) {
            Object thisValue = getFieldValue(thisObject, field);
            Object otherValue = getFieldValue(otherObject, field);
            appendEquals(equalsBuilder, thisValue, otherValue);
        }
        return equalsBuilder.isEquals();
    }

    private static void appendEquals(EqualsBuilder equalsBuilder, Object thisValue, Object otherValue) {
        if (areBigDecimals(thisValue, otherValue)) {
            equalsBuilder.append(0, compareBigDecimals(thisValue, otherValue));
        } else {
            equalsBuilder.append(thisValue, otherValue);
        }
    }

    private static int compareBigDecimals(Object thisValue, Object otherValue) {
        return ((BigDecimal) thisValue).compareTo((BigDecimal) otherValue);
    }

    private static boolean areBigDecimals(Object thisValue, Object otherValue) {
        return thisValue instanceof BigDecimal && otherValue instanceof BigDecimal;
    }

    private static EqualsBuilder constructEqualsBuilder(boolean appendSuper, boolean superEqualsResult) {
        final EqualsBuilder equalsBuilder = new EqualsBuilder();
        if (appendSuper) {
            equalsBuilder.appendSuper(superEqualsResult);
        }
        return equalsBuilder;
    }

    private static Object getFieldValue(Object object, String fieldName) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format(
                    "Cannot obtain field's value for equals method on %s field %s. Reason: %s", object.getClass(),
                    fieldName, e.getMessage()), e);
        }
    }

}
