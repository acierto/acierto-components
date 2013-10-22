package com.aciertoteam.osm.model;

/**
 * @author Bogdan Nechyporenko
 */
public class DefaultStaticOsmRequest extends StaticOsmRequest {

    private static final Size DEFAULT_SIZE = new Size(300, 200);

    private static final int DEFAULT_ZOOM = 18;

    protected DefaultStaticOsmRequest(Size size, int zoom, Coordinate center) {
        super(size, zoom, center);
    }

    public static DefaultStaticOsmRequest createRequest(String x, String y) {
        return new DefaultStaticOsmRequest(DEFAULT_SIZE, DEFAULT_ZOOM, Coordinate.createCenter(x, y));
    }
}
