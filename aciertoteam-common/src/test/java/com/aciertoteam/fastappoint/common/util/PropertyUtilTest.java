package com.aciertoteam.fastappoint.common.util;

import java.util.List;
import java.util.Locale;
import com.aciertoteam.common.utils.PropertyUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Bogdan Nechyporenko
 */
public class PropertyUtilTest {

    @Test
    public void readFileTest() {
        List<String> countries = PropertyUtil.readFile("test-countries", Locale.US);
        Assert.assertTrue(countries.contains("label.country.ukraine"));
        Assert.assertTrue(countries.contains("label.country.united.arab.emirates"));
        Assert.assertTrue(countries.contains("label.country.afghanistan"));
    }
}
