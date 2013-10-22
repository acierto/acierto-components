package com.aciertoteam.osm.services.impl;

import com.aciertoteam.common.utils.SiteContentReader;
import com.aciertoteam.osm.model.StaticOsmRequest;
import com.aciertoteam.osm.services.OpenMapStreetService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;

/**
 * @author Bogdan Nechyporenko
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public class DefaultOpenMapStreetService implements OpenMapStreetService {

    @Value("${mapquest.application.key}")
    private String mapquestApiKey;

    @Override
    public InputStream getStaticPicture(StaticOsmRequest osmRequest) {
        return SiteContentReader.readAsInputStream(osmRequest.getUrl(mapquestApiKey));
    }
}
