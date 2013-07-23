package com.aciertoteam.geo.services;

import com.aciertoteam.geo.entity.Country;

/**
 * @author Bogdan Nechyporenko
 */
public interface GeoIpService {

    /**
     * Returns the country by passed Ip
     *
     * @param ip
     * @return
     */
    Country defineCountry(String ip);
}
