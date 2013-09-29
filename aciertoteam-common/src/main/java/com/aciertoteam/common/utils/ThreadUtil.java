package com.aciertoteam.common.utils;

import com.aciertoteam.common.exceptions.AciertoteamInterruptedException;

/**
 * @author Bogdan Nechyporenko
 */
public class ThreadUtil {

    public static void delay(long delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new AciertoteamInterruptedException(e.getMessage(), e);
        }
    }
}
