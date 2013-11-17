package com.aciertoteam.io;

import com.aciertoteam.io.exceptions.FileException;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author Bogdan Nechyporenko
 */
public final class AciertoTeamFileUtils {

    private static final Logger LOG = Logger.getLogger(AciertoTeamFileUtils.class);

    private AciertoTeamFileUtils() {
        // restrict instantiation
    }

    public static File unzipFile(File zipFile, String outputFolderPath, String newFileName) {
        File outputFile = new File(outputFolderPath, newFileName);
        FileInputStream fin = null;
        ZipInputStream zin = null;
        FileOutputStream fout = null;

        try {
            fin = new FileInputStream(zipFile);
            zin = new ZipInputStream(fin);
            ZipEntry ze;
            while ((ze = zin.getNextEntry()) != null) {
                LOG.info("Unzipping " + ze.getName());
                fout = new FileOutputStream(outputFile);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
            }
            return outputFile;
        } catch (FileNotFoundException e) {
            throw new FileException(e.getMessage(), e);
        } catch (IOException e) {
            throw new FileException(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(fin);
            IOUtils.closeQuietly(zin);
            IOUtils.closeQuietly(fout);
        }
    }

    public static File unzipFile(InputStream fin, String outputFolderPath, String newFileName) {
        File outputFile = new File(outputFolderPath, newFileName);
        ZipInputStream zin;
        FileOutputStream fout = null;

        try {
            BufferedInputStream bin = new BufferedInputStream(fin);
            zin = new ZipInputStream(bin);
            fout = new FileOutputStream(outputFile);

            while (zin.getNextEntry() != null) {
                byte[] buffer = new byte[8192];
                int len;
                while ((len = zin.read(buffer)) != -1) {
                    fout.write(buffer, 0, len);
                }
            }
            return outputFile;
        } catch (FileNotFoundException e) {
            throw new FileException(e.getMessage(), e);
        } catch (IOException e) {
            throw new FileException(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(fout);
            IOUtils.closeQuietly(fin);
        }
    }

}
