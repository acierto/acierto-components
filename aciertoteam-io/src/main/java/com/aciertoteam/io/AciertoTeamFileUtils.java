package com.aciertoteam.io;

import com.aciertoteam.io.exceptions.FileException;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
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

    public static List<File> unzipArchive(InputStream fin, String outputFolder) {
        List<File> files = new ArrayList<File>();
        byte[] buffer = new byte[1024];

        FileOutputStream fos = null;
        ZipInputStream zis;
        try {

            File folder = new File(outputFolder);
            if (!folder.exists()) {
                folder.mkdir();
            }

            zis = new ZipInputStream(fin);
            ZipEntry ze = zis.getNextEntry();

            while (ze != null) {

                String fileName = ze.getName();
                File newFile = new File(outputFolder, fileName);

                new File(newFile.getParent()).mkdirs();

                fos = new FileOutputStream(newFile);

                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }

                files.add(newFile);
                IOUtils.closeQuietly(fos);
                ze = zis.getNextEntry();
            }

        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(fin);
            IOUtils.closeQuietly(fos);
        }
        return files;
    }

}
