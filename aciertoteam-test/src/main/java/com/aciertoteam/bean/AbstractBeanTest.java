package com.aciertoteam.bean;

import static org.junit.Assert.assertEquals;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import com.aciertoteam.util.ReflectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

/**
 * @author ishestiporov
 */
public abstract class AbstractBeanTest<T> {

    private static final Log LOG = LogFactory.getLog(AbstractBeanTest.class);

    @Test
    public void testConstructors() throws InvocationTargetException, IllegalAccessException, InstantiationException {
        for (Constructor constructor : getGenericsClass().getDeclaredConstructors()) {
            ReflectionUtils.callConstructor(constructor);
        }
    }

    @Test
    public void testGettersSetters() {
        Class victimClass = getGenericsClass();
        for (PropertyDescriptor descriptor : BeanUtils.getPropertyDescriptors(victimClass)) {
            if (ReflectionUtils.hasField(victimClass, descriptor)) {
                Object victim = ReflectionUtils.constructNewObject(victimClass);
                checkMethods(victimClass, descriptor, victim);
            }
        }
    }

    private void checkMethods(Class clazz, PropertyDescriptor descriptor, Object victim) {
        Object set = ReflectionUtils.callMethodOrField(descriptor, true, victim);
        Object returned = ReflectionUtils.callMethodOrField(descriptor, false, victim);
        assertField(clazz, descriptor, set, returned);
    }

    private void assertField(Class clazz, PropertyDescriptor descriptor, Object set, Object returned) {
        if (set != null && returned != null) {
            assertEquals(String.format("Class: %s, property: %s", clazz, descriptor.getName()), set, returned);
        } else {
            LOG.info(String.format("Skipped class: %s; property: %s", clazz, descriptor.getName()));
        }
    }

    private Class getGenericsClass() {
        ParameterizedType superClass = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class) superClass.getActualTypeArguments()[0];
    }
}
