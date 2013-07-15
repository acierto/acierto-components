package com.aciertoteam.generator.model;

import java.io.File;
import com.aciertoteam.generator.model.enums.FileFormat;

/**
 * @author Bogdan Nechyporenko
 */
public interface FileResource extends InputResource {

    /**
     * @return
     */
    File getFile();

    /**
     *
     * @return
     */
    FileFormat getFileFormat();

    /**
     * If the file is zipped, the file to be unzipped
     *
     * @return
     */
    File getTextFile();
}
