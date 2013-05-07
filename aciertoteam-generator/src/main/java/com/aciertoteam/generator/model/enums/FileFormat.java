package com.aciertoteam.generator.model.enums;

/**
 * @author Bogdan Nechyporenko
 */
public enum FileFormat {
    TXT, ZIP;

    public boolean isZipped() {
        return this.equals(ZIP);
    }
}
