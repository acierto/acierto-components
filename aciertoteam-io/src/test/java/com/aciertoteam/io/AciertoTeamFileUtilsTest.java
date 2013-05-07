package com.aciertoteam.io;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static junit.framework.Assert.assertEquals;

/**
 * @author Bogdan Nechyporenko
 */
public class AciertoTeamFileUtilsTest {

    private static final String REL_OUTPUT_FOLDER = "output";

    private static final String ROOT_FOLDER = AciertoTeamFileUtilsTest.class.getResource(".").getPath();

    private static final File ZIP_FILE = new File(AciertoTeamFileUtilsTest.class.getResource("/com/aciertoteam/io/test.zip").getPath());

    private static final File EXPECTED_TXT_FILE = new File(AciertoTeamFileUtilsTest.class.getResource("/com/aciertoteam/io/test.txt").getPath());

    @Test
    public void unzipFileTest() throws IOException {
        File outputFile = AciertoTeamFileUtils.unzipFile(ZIP_FILE, createOutputFile().getAbsolutePath(), "newFile.txt");
        assertEquals(FileUtils.readFileToString(EXPECTED_TXT_FILE), FileUtils.readFileToString(outputFile));
    }

    private File createOutputFile() throws IOException {
        return new File(getOrCreateTestOutputFolder(), "");
    }

    private File getOrCreateTestOutputFolder() throws IOException {
        File outputFolder = new File(ROOT_FOLDER, REL_OUTPUT_FOLDER);
        FileUtils.forceMkdir(outputFolder);
        return outputFolder;
    }
}
