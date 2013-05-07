package com.aciertoteam.generator.transformers;

/**
 * @author Bogdan Nechyporenko
 */
public interface Transformer {

    /**
     * Transform the input text depending on specified logic applied in each of transformer's implementations.
     *
     * @param inputText
     * @return
     */
    String transform(String inputText);
}
