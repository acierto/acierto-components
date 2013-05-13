package com.aciertoteam.mail.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.CharEncoding;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

/**
 * @author Bogdan Nechyporenko
 */
@Component
public class FtlReader {

    private static final Logger LOG = Logger.getLogger(FtlReader.class);

    private static final int BYTE_ARRAY_SIZE = 1024 * 1024;

    @Autowired
    private Configuration configuration;

    public String read(String ftlFileName, Map<String, Object> root, Locale locale) {
        String localizedFtlFileName = String.format(ftlFileName + "_%s_%s.ftl", locale.getLanguage(), locale.getCountry());

        Writer out = null;
        try {
            Template template = configuration.getTemplate(localizedFtlFileName);

            ByteArrayOutputStream baos = new ByteArrayOutputStream(BYTE_ARRAY_SIZE);
            out = new OutputStreamWriter(baos, CharEncoding.UTF_8);

            template.process(root, out);
            return baos.toString(CharEncoding.UTF_8);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(out);
        }
        return null;
    }
}
