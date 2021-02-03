package com.github.mzz.json;

import com.alibaba.fastjson.TypeReference;

import java.util.List;
import java.util.Map;

/**
 * The type Type reference util.
 *
 * @author zuozhu.meng
 * @date 2020 /7/29
 */
public class TypeReferenceUtil {

    /**
     * Gets list type reference.
     *
     * @param <T> the type parameter
     * @param cls the cls
     * @return the list type reference
     */
    public static <T> TypeReference<List<T>> getListTypeReference(Class<T> cls) {
        return new TypeReference<List<T>>() {
        };
    }

    /**
     * Gets map type reference.
     *
     * @param <K> the type parameter
     * @param <V> the type parameter
     * @param k   the k
     * @param v   the v
     * @return the map type reference
     */
    public static <K, V> TypeReference<Map<K, V>> getMapTypeReference(Class<K> k, Class<V> v) {
        return new TypeReference<Map<K, V>>() {
        };
    }

}
