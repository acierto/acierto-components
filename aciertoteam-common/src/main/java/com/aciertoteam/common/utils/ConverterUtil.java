package com.aciertoteam.common.utils;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Bogdan Nechyporenko
 */
public class ConverterUtil {

    public static List<Long> convert(List<BigInteger> bigIntegerList) {
        List<Long> list = new LinkedList<Long>();
        for (BigInteger bigInteger : bigIntegerList) {
            list.add(bigInteger.longValue());
        }
        return list;
    }
}
