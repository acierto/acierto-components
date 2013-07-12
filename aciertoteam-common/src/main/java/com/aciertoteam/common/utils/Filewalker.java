package com.aciertoteam.common.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bogdan Nechyporenko
 */
public final class Filewalker {

    private Filewalker() {
        // restrict instantiation
    }

    public static List<File> walk(String path) {
        List<File> files = new ArrayList<File>();
        return walk(path, files);
    }

    private static List<File> walk(String path, List<File> files) {
        File root = new File(path);
        File[] list = root.listFiles();

        if (list == null) {
            return files;
        }

        for (File f : list) {
            if (f.isDirectory()) {
                if (!skipFolder(f)) {
                    walk(f.getAbsolutePath(), files);
                }
            } else {
                files.add(f);
            }
        }

        return files;
    }

    private static boolean skipFolder(File f) {
        return f.getName().startsWith(".");
    }

}
