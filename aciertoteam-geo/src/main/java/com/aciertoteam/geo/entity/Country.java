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
public class Country extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @Column
    private String name;

    private transient String ipAddress;

    Country() {
        //hibernate
    }

    public Country(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public final boolean equals(Object obj) {
        return ContractEqualsBuilder.isEquals(this, obj, "name");
    }

    @Override
    public final int hashCode() {
        return new HashCodeBuilder().append(name).toHashCode();
    }

    @Override
    public String toString() {
        return "Country{" + "name='" + name + '\'' + '}';
    }

}

