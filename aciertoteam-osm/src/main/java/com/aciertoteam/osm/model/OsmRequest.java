package com.aciertoteam.osm.model;

import java.io.Serializable;

/**
 * @author Bogdan Nechyporenko
 */
public interface OsmRequest extends Serializable {

    /**
     * Builds the final url based on the accumulative domain information.
     *
     * @param appKey
     * @return
     */
    String getUrl(String appKey);
}
