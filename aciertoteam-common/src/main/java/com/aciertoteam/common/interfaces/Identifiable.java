package com.aciertoteam.common.interfaces;

/**
 * @author Bogdan Nechyporenko
 */
public interface Identifiable extends StringIdentifiable {

    /**
     * Returns the unique identifier of the object
     *
     * @return
     */
    Long getId();
}
