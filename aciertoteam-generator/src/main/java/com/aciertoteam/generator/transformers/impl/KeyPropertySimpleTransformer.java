package com.aciertoteam.generator.transformers.impl;

import com.aciertoteam.generator.common.CharacterReplacer;
import com.aciertoteam.generator.common.KeyCharacterReplacer;
import com.aciertoteam.generator.transformers.enums.TextCase;

/**
 * @author Bogdan Nechyporenko
 */
public class KeyPropertySimpleTransformer extends SimpleTransformer {

    private CharacterReplacer replacer = new KeyCharacterReplacer();

    public KeyPropertySimpleTransformer(String template, TextCase textCase) {
        super(template, textCase);
    }

    @Override
    public String transform(String inputText) {
        return replacer.replace(super.transform(inputText));
    }
}
