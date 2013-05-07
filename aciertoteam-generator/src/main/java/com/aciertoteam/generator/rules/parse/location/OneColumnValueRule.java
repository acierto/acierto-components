package com.aciertoteam.generator.rules.parse.location;

import com.aciertoteam.generator.rules.parse.ParseRule;

/**
 * @author Bogdan Nechyporenko
 */
public interface OneColumnValueRule extends ParseRule {

    /**
     * Points how many top rows to be skipped before starting process of the file
     *
     * @return
     */
    int getContentStartRow();

    /**
     * Which column to be read
     *
     * @return
     */
    int getValueColumn();

    /**
     * The separator of columns which to be used.
     *
     * @return
     */
    String getSeparator();
}
