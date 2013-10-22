package com.aciertoteam.osm.model;

/**
 * @author Bogdan Nechyporenko
 */
public enum ImageType {
    JPEG, JPG, GIF, PNG;

    private String getCode() {
        return toString().toLowerCase();
    }
}
