package com.github.mzz.common.util;

import java.util.Map;

/**
 * The type Map util.
 *
 * @author mengzz
 */
public class MapUtil {
    /**
     * Is empty.
     *
     * @param map the map
     * @return the boolean
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return (map == null || map.isEmpty());
    }

    /**
     * Is not empty.
     *
     * @param map the map
     * @return the boolean
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * Gets any map value.
     *
     * @param <K> the type parameter
     * @param <V> the type parameter
     * @param map the map
     * @return the any one map value
     */
    public static <K, V> V getAnyValue(Map<K, V> map) {
        return IterableUtils.getAnyNotNull(map.values());
    }

    /**
     * Gets any entry.
     *
     * @param <K> the type parameter
     * @param <V> the type parameter
     * @param map the map
     * @return the any entry
     */
    public static <K, V> Map.Entry<K, V> getAnyEntry(Map<K, V> map) {
        return IterableUtils.getAnyNotNull(map.entrySet());
    }

}
