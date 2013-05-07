package com.aciertoteam.generator.transformers.impl;

import com.aciertoteam.generator.transformers.Transformer;
import com.aciertoteam.generator.transformers.enums.TextCase;

/**
 * @author Bogdan Nechyporenko
 */
public class SimpleTransformer implements Transformer {

    private String template;

    private TextCase textCase;

    public SimpleTransformer(String template, TextCase textCase) {
        this.template = template;
        this.textCase = textCase;
    }

    public SimpleTransformer(String template) {
        this(template, TextCase.DONT_CHANGE);
    }

    @Override
    public String transform(String inputText) {
        return textCase.apply(String.format(template, inputText));
    }
}
