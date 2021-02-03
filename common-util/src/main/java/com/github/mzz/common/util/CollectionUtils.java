package com.github.mzz.common.util;

import java.util.Collection;

/**
 * The type Collection utils.
 *
 * @author mengzz
 */
public class CollectionUtils {

    /**
     * Is empty.
     *
     * @param <E>  the type parameter
     * @param coll the coll
     * @return the boolean
     */
    public static <E> boolean isEmpty(Collection<E> coll) {
        return coll == null || coll.isEmpty();
    }

    /**
     * Is not empty.
     *
     * @param coll the coll
     * @return the boolean
     */
    public static boolean isNotEmpty(Collection<?> coll) {
        return !isEmpty(coll);
    }

}
