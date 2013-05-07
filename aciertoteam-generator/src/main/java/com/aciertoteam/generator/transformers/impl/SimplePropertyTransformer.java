package com.aciertoteam.generator.transformers.impl;

import com.aciertoteam.generator.transformers.PropertyTransformer;
import com.aciertoteam.generator.transformers.Transformer;
import com.aciertoteam.generator.transformers.enums.PropertyType;

/**
 * @author Bogdan Nechyporenko
 */
public class SimplePropertyTransformer<K extends Transformer, V extends Transformer> implements PropertyTransformer {

    private K keyTransformer;
    private V valueTransformer;

    public SimplePropertyTransformer(K keyTransformer, V valueTransformer) {
        this.keyTransformer = keyTransformer;
        this.valueTransformer = valueTransformer;
    }

    @Override
    public String transform(PropertyType propertyType, String inputText) {
        switch (propertyType) {
            case KEY:
                return keyTransformer.transform(inputText);
            case VALUE:
                return valueTransformer.transform(inputText);
            default:
                throw new IllegalArgumentException("Unsupportable enum type: " + propertyType);
        }
    }
}
