package com.aciertoteam.common.comparator;

import org.apache.commons.lang3.builder.CompareToBuilder;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

/**
 * @author Bogdan Nechyporenko
 */
public class LocalizedStringComparator implements Comparator<String> {

    private Collator collator = Collator.getInstance(Locale.getDefault());

    @Override
    public int compare(String string1, String string2) {
        return new CompareToBuilder().append(string1, string2, collator).toComparison();
    }
}
