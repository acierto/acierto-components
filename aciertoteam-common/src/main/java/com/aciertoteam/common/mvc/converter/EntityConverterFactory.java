package com.aciertoteam.common.mvc.converter;

import com.aciertoteam.common.entity.AbstractEntity;
import com.aciertoteam.common.repository.EntityRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * Converter factory that will return converter for AbstractEntity instances
 * based on it's class.
 * @author ishestiporov
 */
public class EntityConverterFactory implements ConverterFactory<String, AbstractEntity> {

    private static final Logger LOG = LoggerFactory.getLogger(EntityConverter.class);

    @Autowired
    private EntityRepository entityRepository;

    @Override
    public <T extends AbstractEntity> Converter<String, T> getConverter(Class<T> targetType) {
        return new EntityConverter<T>(targetType);
    }

    /**
     * Default converter for AbstractEntity instances. Fetches entity by id.
     * @param <T>
     */
    private final class EntityConverter<T extends AbstractEntity> implements Converter<String, T> {

        private final Class<T> clazz;

        private EntityConverter(Class<T> clazz) {
            this.clazz = clazz;
        }

        @Override
        public T convert(String source) {
            if (isLongValue(source)) {
                return entityRepository.findById(clazz, Long.parseLong(source));
            }
            String errorMessage = String.format("Cannot convert non long value %s to entity id.", source);
            LOG.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        private boolean isLongValue(String source) {
            return NumberUtils.isDigits(source) && NumberUtils.isNumber(source);
        }
    }
}
