package com.aciertoteam.generator.common;

/**
 * @author Bogdan Nechyporenko
 */
public interface CharacterReplacer {

    /**
     * As an output gives the replaced value based on defined rules.
     *
     * @param input
     * @return
     */
    String replace(String input);

}
