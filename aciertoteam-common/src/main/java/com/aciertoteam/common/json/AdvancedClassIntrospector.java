package com.aciertoteam.common.json;

import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.introspect.AnnotatedClass;
import org.codehaus.jackson.map.introspect.BasicBeanDescription;
import org.codehaus.jackson.map.introspect.BasicClassIntrospector;
import org.codehaus.jackson.map.type.TypeFactory;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Bogdan Nechyporenko
 */
public class AdvancedClassIntrospector extends BasicClassIntrospector {

    private String[] attributes;

    public AdvancedClassIntrospector(String[] attributes) {
        this.attributes = attributes;
    }

    @Override
    public BasicBeanDescription forSerialization(SerializationConfig cfg, Class<?> clazz, MixInResolver r) {
        AnnotationIntrospector ai = cfg.getAnnotationIntrospector();
        AnnotatedClass ac = AnnotatedClass.construct(clazz, ai, r);
        ac.resolveMemberMethods(getSerializationMethodFilter(cfg), false);
        ac.resolveCreators(true);
        ac.resolveFields(false);

        Map<Class, List<String>> classAttributesMap = new HashMap<Class, List<String>>();
        for (String attribute : attributes) {
            if (attribute.contains(".")) {
                populateChildClassAttribute(clazz, classAttributesMap, attribute);
            } else {
                addAttribute(classAttributesMap, clazz, attribute);
            }
        }

        return new AdvancedBeanDescription(TypeFactory.type(clazz), ac, ai, attributes);
    }

    private void populateChildClassAttribute(Class<?> parentClass, Map<Class, List<String>> childClassAttributes,
            String attribute) {
        String[] attrs = attribute.split(".");
        Class<?> childClassType = BeanUtils.getPropertyDescriptor(parentClass, attrs[0]).getPropertyType();
        addAttribute(childClassAttributes, childClassType, attrs[1]);
    }

    private void addAttribute(Map<Class, List<String>> childClassAttributes, Class<?> clazz, String attribute) {
        List<String> attributes = childClassAttributes.get(clazz);
        if (attributes == null) {
            attributes = new ArrayList<String>();
        }
        attributes.add(attribute);
    }
}
