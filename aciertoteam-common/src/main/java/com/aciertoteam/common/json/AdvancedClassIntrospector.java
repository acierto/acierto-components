package com.aciertoteam.common.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.introspect.AnnotatedClass;
import org.codehaus.jackson.map.introspect.BasicBeanDescription;
import org.codehaus.jackson.map.introspect.BasicClassIntrospector;
import org.codehaus.jackson.map.type.TypeFactory;
import org.springframework.beans.BeanUtils;

/**
 * @author Bogdan Nechyporenko
 */
public class AdvancedClassIntrospector extends BasicClassIntrospector {

    private Map<Class, List<String>> classAttributesMap = new HashMap<Class, List<String>>();

    public AdvancedClassIntrospector(Class clazz, String[] attributes) {
        for (String attribute : attributes) {
            if (attribute.contains(".")) {
                populateChildClassAttribute(clazz, classAttributesMap, attribute);
            } else {
                addAttribute(classAttributesMap, clazz, attribute);
            }
        }
    }

    @Override
    public BasicBeanDescription forSerialization(SerializationConfig cfg, Class<?> clazz, MixInResolver r) {
        AnnotationIntrospector ai = cfg.getAnnotationIntrospector();
        AnnotatedClass ac = AnnotatedClass.construct(clazz, ai, r);
        ac.resolveMemberMethods(getSerializationMethodFilter(cfg), false);
        ac.resolveCreators(true);
        ac.resolveFields(false);

        List<String> attributes = classAttributesMap.get(clazz);
        String[] attrs = attributes == null ? new String[]{"*"} : attributes.toArray(new String[attributes.size()]);

        return new AdvancedBeanDescription(TypeFactory.type(clazz), ac, ai, attrs);
    }

    private void populateChildClassAttribute(Class<?> parentClass, Map<Class, List<String>> classAttributesMap,
                                             String attribute) {
        String[] attrs = attribute.split("\\.");
        Class<?> childClassType = BeanUtils.getPropertyDescriptor(parentClass, attrs[0]).getPropertyType();
        addAttribute(classAttributesMap, childClassType, attrs[1]);
        addAttribute(classAttributesMap, parentClass, attrs[0]);

        String tailAttribute = attribute.substring(attribute.indexOf(".") + 1);
        if (tailAttribute.contains(".")) {
            populateChildClassAttribute(childClassType, classAttributesMap, tailAttribute);
        }
    }

    private void addAttribute(Map<Class, List<String>> childClassAttributes, Class<?> clazz, String attribute) {
        List<String> attributes = childClassAttributes.get(clazz);
        if (attributes == null) {
            attributes = new ArrayList<String>();
            childClassAttributes.put(clazz, attributes);
        }
        attributes.add(attribute);
    }
}
