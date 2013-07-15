package com.aciertoteam.bean;

import static org.junit.Assert.assertEquals;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import com.aciertoteam.util.ReflectionUtils;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

/**
 * @author ishestiporov
 */
public abstract class AbstractBeanTest<T> {

    @Test
    public void testConstructors() throws InvocationTargetException, IllegalAccessException, InstantiationException {
        for (Constructor constructor : getGenericsClass().getDeclaredConstructors()) {
            ReflectionUtils.callConstructor(constructor);
        }
    }

    @Test
    public void testGettersSetters() {
        Class victimClass = getGenericsClass();
        if (!Exception.class.isAssignableFrom(victimClass)) {
            for (PropertyDescriptor descriptor : BeanUtils.getPropertyDescriptors(victimClass)) {
                if (descriptor.getWriteMethod() != null) {
                    Object victim = ReflectionUtils.constructNewObject(victimClass);
                    Object set = ReflectionUtils.invokeSetter(victim, descriptor);
                    Object returned = ReflectionUtils.invokeGetter(victim, descriptor);
                    if (set != null && returned != null) {
                        assertField(victimClass, descriptor, set, returned);
                    } else if (ReflectionUtils.hasField(victimClass, descriptor)) {
                        returned = ReflectionUtils.getFieldValue(victim, descriptor);
                        assertField(victimClass, descriptor, set, returned);
                    }
                }
            }
        }
    }

    private void assertField(Class clazz, PropertyDescriptor descriptor, Object set, Object returned) {
        assertEquals(String.format("Class: %s, method: %s", clazz, descriptor.getName()), set, returned);
    }

    private Class getGenericsClass() {
        ParameterizedType superClass = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class) superClass.getActualTypeArguments()[0];
    }
}
