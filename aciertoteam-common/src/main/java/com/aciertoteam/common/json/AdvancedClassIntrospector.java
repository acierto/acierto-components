package com.aciertoteam.common.json;

import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.introspect.AnnotatedClass;
import org.codehaus.jackson.map.introspect.BasicBeanDescription;
import org.codehaus.jackson.map.introspect.BasicClassIntrospector;
import org.codehaus.jackson.map.type.TypeFactory;

/**
 * @author Bogdan Nechyporenko
 */
public class AdvancedClassIntrospector extends BasicClassIntrospector {

    private String[] attributes;

    public AdvancedClassIntrospector(String[] attributes) {
        this.attributes = attributes;
    }

    @Override
    public BasicBeanDescription forSerialization(SerializationConfig cfg, Class<?> c, MixInResolver r) {
        AnnotationIntrospector ai = cfg.getAnnotationIntrospector();
        AnnotatedClass ac = AnnotatedClass.construct(c, ai, r);
        ac.resolveMemberMethods(getSerializationMethodFilter(cfg), false);
        ac.resolveCreators(true);
        ac.resolveFields(false);
        return new AdvancedBeanDescription(TypeFactory.type(c), ac, ai, attributes);
    }
}
