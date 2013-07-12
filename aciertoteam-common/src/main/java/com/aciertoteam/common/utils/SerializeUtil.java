package com.aciertoteam.common.utils;

import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author Bogdan Nechyporenko
 */
public final class SerializeUtil {

    private static final Logger LOG = Logger.getLogger(SerializeUtil.class);

    private SerializeUtil() {
        // restrict instantiation
    }

    public static byte[] write(Serializable serializable) {
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oout = null;
        try {
            baos = new ByteArrayOutputStream();
            oout = new ObjectOutputStream(baos);
            oout.writeObject(serializable);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            try {
                if (oout != null) {
                    oout.close();
                }
            } catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return baos.toByteArray();
    }

    public static Serializable read(byte[] bytes) {
        if (bytes == null) {
            return null;
        }

        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
            return (Serializable) ois.readObject();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return null;
    }
}
