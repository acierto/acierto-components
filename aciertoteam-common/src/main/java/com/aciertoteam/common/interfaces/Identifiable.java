package com.aciertoteam.common.interfaces;

import java.io.Serializable;

/**
 * @author Bogdan Nechyporenko
 */
public interface Identifiable extends Serializable, StringIdentifiable {

    /**
     * Returns the unique identifier of the object
     * 
     * @return
     */
    Long getId();
}
