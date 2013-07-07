package com.aciertoteam.common.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Bogdan Nechyporenko
 */
public class Filewalker {

    public static List<File> walk(String path) {

        List<File> files = new ArrayList<File>();

        File root = new File(path);
        File[] list = root.listFiles();

        if (list == null) {
            return Collections.emptyList();
        }

        for (File f : list) {
            if (f.isDirectory()) {
                walk(f.getAbsolutePath());
            } else {
                files.add(f);
            }
        }

        return files;
    }

}
