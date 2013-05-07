package com.aciertoteam.generator.transformers;

import com.aciertoteam.generator.transformers.impl.CountryNameTransformer;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author Bogdan Nechyporenko
 */
public class CountryNameTransformerTest {

    private CountryNameTransformer countryNameTransformer = new CountryNameTransformer();

    @Test
    public void transformTest() {
        assertEquals("Turkmenistan", countryNameTransformer.transform("Turkmenistan"));
        assertEquals("United Republic of Tanzania", countryNameTransformer.transform("Tanzania, United Republic of"));
    }
}
