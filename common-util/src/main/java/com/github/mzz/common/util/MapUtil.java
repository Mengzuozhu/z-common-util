package com.github.mzz.common.util;

import org.apache.commons.collections4.IterableUtils;

import java.util.Map;
import java.util.Objects;

/**
 * The type Map util.
 *
 * @author mengzz
 */
public class MapUtil {

    /**
     * Gets any map value.
     *
     * @param <K> the type parameter
     * @param <V> the type parameter
     * @param map the map
     * @return the any one map value
     */
    public static <K, V> V getAnyValue(Map<K, V> map) {
        return IterableUtils.find(map.values(), Objects::nonNull);
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
        return IterableUtils.find(map.entrySet(), Objects::nonNull);
    }

}
