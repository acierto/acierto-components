package com.aciertoteam.osm.services;

import com.aciertoteam.osm.model.StaticOsmRequest;

import java.io.InputStream;

/**
 * @author Bogdan Nechyporenko
 */
public interface OpenMapStreetService {

    /**
     * Execute external call to fetch image in binary format.
     *
     * @param osmRequest
     * @return
     */
    InputStream getStaticPicture(StaticOsmRequest osmRequest);

}
