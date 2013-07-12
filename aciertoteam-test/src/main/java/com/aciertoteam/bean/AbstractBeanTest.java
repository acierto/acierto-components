package com.aciertoteam.bean;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import java.beans.PropertyDescriptor;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import com.aciertoteam.util.ReflectionUtils;
import org.apache.commons.lang3.ClassUtils;
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
        for (PropertyDescriptor descriptor : BeanUtils.getPropertyDescriptors(victimClass)) {
            if (ReflectionUtils.hasField((victimClass), descriptor.getName())) {
                Object victim = ReflectionUtils.constructNewObject(victimClass);
                Object set = ReflectionUtils.invokeSetter(victim, descriptor);
                Object returned = ReflectionUtils.invokeGetter(victim, descriptor);
                if (set != null && returned != null) {
                    assertEquals(set, returned);
                }
            }
        }
    }

    private Class getGenericsClass() {
        ParameterizedType superClass = (ParameterizedType) this.getClass().getGenericSuperclass();
        return (Class) superClass.getActualTypeArguments()[0];
    }
}
