package com.aciertoteam.i18n.comparator;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

import com.aciertoteam.geo.entity.Country;
import com.aciertoteam.i18n.message.LocalizedMessageSource;
import org.apache.commons.lang3.builder.CompareToBuilder;

/**
 * @author Bogdan Nechyporenko
 */
public class CountryComparator implements Comparator<Country> {

    private final LocalizedMessageSource messageSource;

    public CountryComparator(LocalizedMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public int compare(Country country1, Country country2) {
        Collator collator = Collator.getInstance(Locale.getDefault());
        String countryLabel1 = messageSource.getMessage(country1.getName());
        String countryLabel2 = messageSource.getMessage(country2.getName());
        return new CompareToBuilder().append(countryLabel1, countryLabel2, collator).toComparison();
    }
}
