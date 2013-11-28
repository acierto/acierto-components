package com.aciertoteam.common.json;

import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.introspect.AnnotatedClass;
import org.codehaus.jackson.map.introspect.AnnotatedMethod;
import org.codehaus.jackson.map.introspect.BasicBeanDescription;
import org.codehaus.jackson.map.introspect.VisibilityChecker;
import org.codehaus.jackson.type.JavaType;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ishestiporov
 */
public class CompositeBeanDescription extends BasicBeanDescription {

    private Map<Class<?>, List<String>> classProperties;

    public CompositeBeanDescription(JavaType type, AnnotatedClass ac, AnnotationIntrospector ai,
            Map<Class<?>, List<String>> classProperties) {
        super(type, ac, ai);
        this.classProperties = classProperties;
    }

    @Override
    public LinkedHashMap<String, AnnotatedMethod> findGetters(VisibilityChecker<?> visibilityChecker,
            Collection<String> ignoredProperties) {

        LinkedHashMap<String, AnnotatedMethod> gettersFiltered = new LinkedHashMap<String, AnnotatedMethod>();

        return gettersFiltered;
    }

}
