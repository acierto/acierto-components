package com.aciertoteam.common.resolver;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation specifying which fields of the entity can be updated by the
 * EntityHandlerMethodArgumentResolver during the MVC binding.
 * 
 * @author ishestiporov
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BindEntity {

    public static final String ALL = "*";

    /**
     * The name of the entity fields to bind parameters to.
     */
    String[] fields() default ALL;

    /**
     * Specify required fields. Set {ALL} to make all fields required
     * @return
     */
    String[] required() default ALL;

    /**
     * Set to false to restrict creation of entities
     * @return
     */
    boolean allowCreate() default true;
}
