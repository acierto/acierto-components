package com.aciertoteam.geo.services.impl;

import com.aciertoteam.common.service.impl.DefaultEntityService;
import com.aciertoteam.geo.entity.Country;
import com.aciertoteam.geo.services.GeoIpService;
import com.maxmind.geoip.LookupService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * @author Bogdan Nechyporenko
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class DefaultGeoIpService extends DefaultEntityService implements GeoIpService {

    private static final Logger LOG = Logger.getLogger(DefaultGeoIpService.class);

    @Value("${geo.ip.file.path}")
    private String geoIpFilePath;

    @Override
    public Country defineCountry(String ipAddress) {
        try {
            String countryName = getLookupService().getCountry(ipAddress).getName();
            String countryLabel = String.format("label.country.%s", countryName).toLowerCase();
            Country country = findByField(Country.class, "name", countryLabel);
            country.setIpAddress(ipAddress);
            return country;
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        return null;
    }

    private LookupService getLookupService() throws IOException {
        String filePath = getClass().getResource(geoIpFilePath).getFile();
        return new LookupService(filePath, LookupService.GEOIP_MEMORY_CACHE);
    }
}

