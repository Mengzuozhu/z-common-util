package com.github.mzz.common.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collector;

/**
 * 集合操作
 *
 * @author mengzz
 **/
public class CollectionOp {

    /**
     * 交集
     *
     * @param <T>         the type parameter
     * @param <R>         the type parameter
     * @param collection1 the collection 1
     * @param collection2 the collection 2
     * @param collector   the collector
     * @return the r
     */
    public static <T, R> R intersection(Collection<T> collection1, Collection<T> collection2,
                                        Collector<T, ?, R> collector) {
        if (collection1 == null || collection2 == null) {
            return null;
        }
        Set<T> uniques = new HashSet<>(collection1);
        return collection2.stream()
                .filter(uniques::contains)
                .collect(collector);
    }

}
