package com.aciertoteam.osm.model;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.text.MessageFormat;

/**
 * Represents the coordinate on the map.
 *
 * It can be or the center of the map either the marker(s) on it.
 *
 * @author Bogdan Nechyporenko
 */
public class Coordinate implements Serializable {

    private final String color;
    private final String x;
    private final String y;

    private Coordinate(String x, String y, String color) {
        this.x = x;
        this.y = y;
        this.color = color;
        checkOnBlank(x, y, color);
    }

    private Coordinate(String x, String y) {
        this.color = null;
        this.x = x;
        this.y = y;
        checkOnBlank(x, y);
    }

    public static Coordinate createCenter(String x, String y) {
        return new Coordinate(x, y);
    }

    public static Coordinate createMarker(String x, String y, String color) {
        return new Coordinate(x, y, color);
    }

    private void checkOnBlank(String... params) {
        for (String param : params) {
            if (isBlank(param)) {
                throw new IllegalStateException(composeErrorMessage());
            }
        }
    }

    private String composeErrorMessage() {
        return MessageFormat.format("{0} has empty values. Current state is : {1}", getClassName(), this);
    }

    private String getClassName() {
        return getClass().getSimpleName();
    }

    private boolean isBlank(String param) {
        return StringUtils.isBlank(param);
    }

    public String getColor() {
        return color;
    }

    public String getX() {
        return x;
    }

    public String getY() {
        return y;
    }

    @Override
    public String toString() {
        if (StringUtils.isBlank(color)) {
            return MessageFormat.format("{0},{1}", x, y);
        }
        return MessageFormat.format("{0},{1},{2}", color, x, y);
    }
}
