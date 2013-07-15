package com.aciertoteam.common.utils;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.CharEncoding;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.ContentEncodingHttpClient;
import org.apache.log4j.Logger;

/**
 * @author Bogdan Nechyporenko
 */
public final class SiteContentReader {

    private static final Logger LOG = Logger.getLogger(SiteContentReader.class);

    private SiteContentReader() {
        // restrict instantiation
    }

    @SuppressWarnings("unchecked")
    public static InputStream readAsInputStream(String url) {

        HttpClient httpclient = new ContentEncodingHttpClient();
        HttpGet httpget = new HttpGet(url);
        try {
            HttpResponse response = httpclient.execute(httpget);
            return response.getEntity().getContent();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static String readContent(String url) {

        HttpClient httpclient = new ContentEncodingHttpClient();
        HttpGet httpget = new HttpGet(url);
        try {
            HttpResponse response = httpclient.execute(httpget);
            return IOUtils.toString(response.getEntity().getContent(), CharEncoding.UTF_8).trim();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }

}
