package com.aciertoteam.common.interfaces;

import java.io.Serializable;

/**
 * @author ishestiporov
 */
public interface StringIdentifiable extends Serializable {

    /**
     * Returns the unique identifier as a String
     * 
     * @return
     */
    String getStringId();
}
