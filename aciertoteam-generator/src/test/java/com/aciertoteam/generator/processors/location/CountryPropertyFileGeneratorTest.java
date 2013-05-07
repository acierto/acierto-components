package com.aciertoteam.generator.processors.location;

import com.aciertoteam.generator.model.DefaultFileResource;
import com.aciertoteam.generator.model.FileResource;
import com.aciertoteam.generator.model.enums.FileFormat;
import com.aciertoteam.generator.processors.location.impl.DefaultCountryPropertyFileGenerator;
import com.aciertoteam.generator.rules.parse.location.OneColumnValueRule;
import com.aciertoteam.generator.rules.parse.location.impl.DefaultOneColumnValueRule;
import com.aciertoteam.generator.transformers.enums.TextCase;
import com.aciertoteam.generator.transformers.impl.CountryNameTransformer;
import com.aciertoteam.generator.transformers.impl.KeyPropertySimpleTransformer;
import com.aciertoteam.generator.transformers.impl.SimplePropertyTransformer;
import com.aciertoteam.generator.transformers.impl.SimpleTransformer;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static junit.framework.Assert.assertTrue;

/**
 * @author Bogdan Nechyporenko
 */
public class CountryPropertyFileGeneratorTest {

    private CountryPropertyFileGenerator countryPropertyFileGenerator = new DefaultCountryPropertyFileGenerator();

    private static final File INPUT_TEXT_FILE = new File(CountryPropertyFileGeneratorTest.class.getResource("GEODATASOURCE-COUNTRY.TXT").getPath());

    @Test
    public void generateFileTest() throws IOException {
        InputStream inputStream = countryPropertyFileGenerator.
                generate(createFileResource(), createCountryFileLocationRule(), createPropertyTransformer());

        String result = IOUtils.toString(inputStream).trim();
        System.out.println(result);
        assertTrue(getExpectedOutput().equals(result));
    }

    private FileResource createFileResource() {
        return new DefaultFileResource(INPUT_TEXT_FILE, FileFormat.TXT);
    }

    private String getExpectedOutput() {
        String result = "label.country.aruba=Aruba\n" +
                "label.country.antigua.and.barbuda=Antigua and Barbuda\n" +
                "label.country.united.arab.emirates=United Arab Emirates";
        return result.replaceAll("\n", System.getProperty("line.separator"));
    }

    private OneColumnValueRule createCountryFileLocationRule() {
        return new DefaultOneColumnValueRule(1, 3, "\t");
    }

    private SimplePropertyTransformer createPropertyTransformer() {
        return new SimplePropertyTransformer<SimpleTransformer, CountryNameTransformer>(
                new KeyPropertySimpleTransformer("label.country.%s", TextCase.LOWER_CASE),
                new CountryNameTransformer()
        );
    }
}
