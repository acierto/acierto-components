package com.aciertoteam.generator.common;

/**
 * @author Bogdan Nechyporenko
 */
public class KeyCharacterReplacer implements CharacterReplacer {

    @Override
    public String replace(String input) {
        return input.
                replaceAll(" ", ".").
                replaceAll("\'", "").
                replaceAll(",", "").
                replaceAll("\\)", "").
                replaceAll("\\(", "").
                replaceAll("\"", "");
    }
}
