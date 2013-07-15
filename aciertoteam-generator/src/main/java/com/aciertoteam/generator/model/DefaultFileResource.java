package com.aciertoteam.generator.model;

import java.io.File;
import com.aciertoteam.generator.model.enums.FileFormat;
import com.aciertoteam.io.AciertoTeamFileUtils;
import org.apache.commons.io.FilenameUtils;

/**
 * @author Bogdan Nechyporenko
 */
public class DefaultFileResource implements FileResource {

    private File file;

    private FileFormat fileFormat;

    public DefaultFileResource(File file, FileFormat fileFormat) {
        this.file = file;
        this.fileFormat = fileFormat;
    }

    @Override
    public File getFile() {
        return file;
    }

    @Override
    public FileFormat getFileFormat() {
        return fileFormat;
    }

    @Override
    public File getTextFile() {
        if (fileFormat.isZipped()) {
            String fileName = file.getName();
            String newFileName = String.format("%s.txt", FilenameUtils.getBaseName(fileName));
            return AciertoTeamFileUtils.unzipFile(file, file.getParentFile().getAbsolutePath(), newFileName);
        }
        return file;
    }
}
