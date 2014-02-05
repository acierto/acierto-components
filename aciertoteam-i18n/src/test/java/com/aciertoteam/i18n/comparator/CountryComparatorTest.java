package com.aciertoteam.i18n.comparator;

import com.aciertoteam.geo.entity.Country;
import com.aciertoteam.i18n.message.LocalizedMessageSource;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Bogdan Nechyporenko
 */
public class CountryComparatorTest {

    private CountryComparator countryComparator;
    private LocalizedMessageSource messageSource = mock(LocalizedMessageSource.class);

    @Before
    public void setUp() {
        countryComparator = new CountryComparator(messageSource);
    }

    @Test
    public void compareTest() {
        String ukraine = "Ukraine";
        String moldova = "Moldova";
        when(messageSource.getMessage(ukraine)).thenReturn(ukraine);
        when(messageSource.getMessage(moldova)).thenReturn(moldova);
        assertEquals(1, countryComparator.compare(new Country(ukraine), new Country(moldova)));
    }

    @Test
    public void compare2Test() {
        String austria = "Austria";
        String netherlands = "Netherlands";
        when(messageSource.getMessage(austria)).thenReturn(austria);
        when(messageSource.getMessage(netherlands)).thenReturn(netherlands);
        assertEquals(-1, countryComparator.compare(new Country(austria), new Country(netherlands)));
    }

    @Test
    public void compare3Test() {
        String latvia1 = "Latvia";
        String latvia2 = "Latvia";
        when(messageSource.getMessage(latvia1)).thenReturn(latvia1);
        when(messageSource.getMessage(latvia2)).thenReturn(latvia2);
        assertEquals(0, countryComparator.compare(new Country(latvia1), new Country(latvia2)));
    }
}
