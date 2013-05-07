package com.aciertoteam.generator.processors.location;

import com.aciertoteam.generator.model.FileResource;
import com.aciertoteam.generator.processors.PropertyFileGenerator;
import com.aciertoteam.generator.rules.parse.location.OneColumnValueRule;
import com.aciertoteam.generator.transformers.PropertyTransformer;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Bogdan Nechyporenko
 */
public interface CountryPropertyFileGenerator extends PropertyFileGenerator {

    /**
     * Generates property file stream from input resource having information in table structure by applying
     * specified rules defined as one more extra parameter.
     *
     * @param fileResource
     * @param rule
     * @param propertyTransformer
     * @return
     */
    InputStream generate(FileResource fileResource, OneColumnValueRule rule, PropertyTransformer propertyTransformer) throws IOException;
}
