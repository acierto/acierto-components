package com.aciertoteam.osm.model;

import java.io.Serializable;
import java.text.MessageFormat;

/**
 * Represents the size of map.
 *
 * @author Bogdan Nechyporenko
 */
public class Size implements Serializable {

    private final int width;
    private final int height;

    public Size(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0},{1}", width, height);
    }
}
