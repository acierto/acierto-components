package com.aciertoteam.common.json;

import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.introspect.AnnotatedClass;
import org.codehaus.jackson.map.introspect.AnnotatedMethod;
import org.codehaus.jackson.map.introspect.BasicBeanDescription;
import org.codehaus.jackson.map.introspect.VisibilityChecker;
import org.codehaus.jackson.type.JavaType;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Bogdan Nechyporenko
 */
public class AdvancedBeanDescription extends BasicBeanDescription {

    private String[] attributes;
    private boolean noDefaultIncludes;

    public AdvancedBeanDescription(JavaType type, boolean noDefaultIncludes, AnnotatedClass ac, AnnotationIntrospector ai, String[] attributes) {
        super(type, ac, ai);
        this.attributes = attributes;
        this.noDefaultIncludes = noDefaultIncludes;
    }

    @Override
    public LinkedHashMap<String, AnnotatedMethod> findGetters(VisibilityChecker<?> visibilityChecker,
                                                              Collection<String> ignoredProperties) {

        LinkedHashMap<String, AnnotatedMethod> getters = super.findGetters(visibilityChecker, ignoredProperties);
        LinkedHashMap<String, AnnotatedMethod> gettersFiltered = new LinkedHashMap<String, AnnotatedMethod>();

        if (!noDefaultIncludes) {
            addProperty(getters, gettersFiltered, "id");
            addProperty(getters, gettersFiltered, "name");
        }

        if (attributes != null) {
            if (attributes.length == 1 && "*".equals(attributes[0])) {
                return getters;
            }
            for (String propertyName : attributes) {
                addProperty(getters, gettersFiltered, propertyName);
            }
        }
        return gettersFiltered;
    }

    private void addProperty(Map<String, AnnotatedMethod> getters, Map<String, AnnotatedMethod> gettersFiltered,
                             String propertyName) {
        if (getters.containsKey(propertyName)) {
            gettersFiltered.put(propertyName, getters.get(propertyName));
        }
    }
}
