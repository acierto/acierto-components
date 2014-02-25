package com.aciertoteam.osm.model;

import java.math.BigDecimal;

/**
 * @author Bogdan Nechyporenko
 */
public class DefaultStaticOsmRequest extends StaticOsmRequest {

    private static final Size DEFAULT_SIZE = new Size(600, 450);
    private static final int DEFAULT_ZOOM = 15;

    protected DefaultStaticOsmRequest(Size size, int zoom, Coordinate center) {
        super(size, zoom, center);
    }

    public static DefaultStaticOsmRequest createRequest(String x, String y) {
        return new DefaultStaticOsmRequest(DEFAULT_SIZE, DEFAULT_ZOOM, Coordinate.createCenter(x, y));
    }

    public static DefaultStaticOsmRequest createRequest(BigDecimal latitude, BigDecimal longitude, int zoom) {
        return new DefaultStaticOsmRequest(DEFAULT_SIZE, zoom, Coordinate.createCenter(latitude, longitude));
    }
}
