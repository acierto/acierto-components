package com.aciertoteam.common.resolver;

import com.aciertoteam.common.entity.AbstractEntity;

/**
 * Entity post processor that will be called for every entity and it's association entity which is mapped through
 * {@link EntityHandlerMethodArgumentResolver
 * 
 * @author ishestiporov
 */
public interface EntityArgumentResolverPostProcessor {

    /**
     * °Called when {@link EntityHandlerMethodArgumentResolver} receives an
     * entity. Will be called only once for every unique entity.
     * @param abstractEntity
     */
    void process(AbstractEntity abstractEntity);
}
