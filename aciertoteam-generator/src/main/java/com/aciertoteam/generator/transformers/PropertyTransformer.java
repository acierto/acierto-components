package com.aciertoteam.generator.transformers;

import com.aciertoteam.generator.transformers.enums.PropertyType;

/**
 * @author Bogdan Nechyporenko
 */
public interface PropertyTransformer {

    /**
     * Transform the input text depending on specified logic applied in each of transformer's implementations.
     *
     * @param inputText
     * @return
     */
    String transform(PropertyType propertyType, String inputText);
}
