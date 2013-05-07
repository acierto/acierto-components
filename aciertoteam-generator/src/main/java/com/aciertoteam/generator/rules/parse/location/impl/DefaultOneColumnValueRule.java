package com.aciertoteam.generator.rules.parse.location.impl;

import com.aciertoteam.generator.rules.parse.location.OneColumnValueRule;

/**
 * @author Bogdan Nechyporenko
 */
public class DefaultOneColumnValueRule implements OneColumnValueRule {

    private int contentStartRow;

    private int countryColumn;

    private String separator;

    public DefaultOneColumnValueRule(int contentStartRow, int countryColumn, String separator) {
        this.contentStartRow = contentStartRow;
        this.countryColumn = countryColumn;
        this.separator = separator;
    }

    @Override
    public int getContentStartRow() {
        return contentStartRow;
    }

    @Override
    public int getValueColumn() {
        return countryColumn;
    }

    @Override
    public String getSeparator() {
        return separator;
    }
}
