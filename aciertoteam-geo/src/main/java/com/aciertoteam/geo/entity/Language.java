package com.aciertoteam.geo.entity;

import com.aciertoteam.common.entity.AbstractEntity;
import com.aciertoteam.common.utils.ContractEqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author Bogdan Nechyporenko
 */
@Entity
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
public class Language extends AbstractEntity {

    @Column(unique = true, nullable = false)
    private String code;

    @Column
    private String countryCode;

    @Column(unique = true, nullable = false)
    private String englishName;

    @Column//(unique = true, nullable = false)    TODO: uncomment only after fixing population of data
    private String nativeLanguageName;

    @Column
    private Boolean supported;

    Language() {
        //hibernate
    }

    // to be removed when files with full data (+ country code) be provided.
    public Language(String code, String englishName, String nativeLanguageName) {
        this(code, "", englishName, nativeLanguageName);
    }

    public Language(String code, String countryCode, String englishName, String nativeLanguageName) {
        this.code = code;
        this.countryCode = countryCode;
        this.englishName = englishName;
        this.nativeLanguageName = nativeLanguageName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getLocaleCode() {
        return code + "_" + countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getNativeLanguageName() {
        return nativeLanguageName;
    }

    public void setNativeLanguageName(String nativeLanguageName) {
        this.nativeLanguageName = nativeLanguageName;
    }

    public boolean isSupported() {
        return Boolean.TRUE.equals(supported);
    }

    @Override
    public final boolean equals(Object obj) {
        return ContractEqualsBuilder.isEquals(this, obj, "code", "englishName", "nativeLanguageName");
    }

    @Override
    public final int hashCode() {
        return new HashCodeBuilder().append(code).append(englishName).append(nativeLanguageName).toHashCode();
    }

    @Override
    public String toString() {
        return "Language{code='" + code + "\', countryCode='" + countryCode +
                "\', englishName='" + englishName + "\', nativeLanguageName='" + nativeLanguageName + "\'}";
    }

    public void markAsSupported() {
        this.supported = true;
    }
}
