package com.aciertoteam.osm.services.impl;

import com.aciertoteam.osm.model.DefaultStaticOsmRequest;
import com.aciertoteam.osm.model.StaticOsmRequest;
import jodd.bean.BeanUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;

import static org.junit.Assert.assertTrue;

/**
 * @author Bogdan Nechyporenko
 */
public class DefaultOpenMapStreetServiceTest {

    private DefaultOpenMapStreetService victim = new DefaultOpenMapStreetService();

    @Test
    public void getStaticPictureTest() throws IOException {
        String appKey = ResourceBundle.getBundle("aciertoteam-osm-test").getString("mapquest.application.key");
        StaticOsmRequest request = DefaultStaticOsmRequest.createRequest("40.0378", "-76.305801");
        BeanUtil.setDeclaredPropertyForced(victim, "mapquestApiKey", appKey);

        InputStream inputStream = victim.getStaticPicture(request);
        byte[] picture = IOUtils.toByteArray(inputStream);

        flushToFileAndCheck(picture);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void flushToFileAndCheck(byte[] picture) throws IOException {
        File tempFile = File.createTempFile("osm", ".png");
        try {
            tempFile.createNewFile();
            FileUtils.writeByteArrayToFile(tempFile, picture);
            assertTrue(tempFile.length() > 0);
        } finally {
            FileUtils.deleteQuietly(tempFile);
        }
    }
}
