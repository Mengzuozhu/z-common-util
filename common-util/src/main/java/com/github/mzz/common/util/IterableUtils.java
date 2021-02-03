package com.github.mzz.common.util;

/**
 * The type Iterable utils.
 *
 * @author mengzz
 */
public class IterableUtils {
    /**
     * Gets any.
     *
     * @param <T>      the type parameter
     * @param iterable the iterable
     * @return the any
     */
    public static <T> T getAnyNotNull(Iterable<T> iterable) {
        if (iterable == null) {
            return null;
        }
        for (T value : iterable) {
            if (value != null) {
                return value;
            }
        }
        return null;
    }
}
