package com.aciertoteam.common.resolver;

import com.aciertoteam.common.entity.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ishestiporov
 */
class DefaultLoggingPostProcessor implements EntityArgumentResolverPostProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultLoggingPostProcessor.class);

    @Override
    public void process(AbstractEntity abstractEntity) {
        LOGGER.debug(String.format("Entity with class %s was bind to controller", abstractEntity.getClass()));
    }
}
