package com.aciertoteam.generator.processors.location.impl;

import com.aciertoteam.generator.model.FileResource;
import com.aciertoteam.generator.processors.location.CityPropertyFileGenerator;
import com.aciertoteam.generator.rules.parse.location.OneColumnValueRule;
import com.aciertoteam.generator.transformers.PropertyTransformer;
import com.aciertoteam.generator.transformers.enums.PropertyType;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Bogdan Nechyporenko
 */
public class DefaultCityPropertyFileGenerator implements CityPropertyFileGenerator {

    @Override
    public InputStream generate(FileResource fileResource, OneColumnValueRule rule, PropertyTransformer propertyTransformer) throws IOException {
        StringBuilder countries = new StringBuilder();

        int index = 0;
        for (Object line : FileUtils.readLines(fileResource.getTextFile())) {
            if (index++ >= rule.getContentStartRow()) {
                appendEntry(countries, line, rule, propertyTransformer);
            }
        }

        return IOUtils.toInputStream(countries.toString());
    }

    private void appendEntry(StringBuilder result, Object line, OneColumnValueRule rule, PropertyTransformer propertyTransformer) {
        String[] columns = ((String) line).split(rule.getSeparator());

        if (columns.length > rule.getValueColumn()) {
            String columnValue = columns[rule.getValueColumn()];

            String key = propertyTransformer.transform(PropertyType.KEY, columnValue);
            String value = propertyTransformer.transform(PropertyType.VALUE, columnValue);

            result.append(String.format("%s=%s%s", key, value, System.getProperty("line.separator")));
        }
    }
}
