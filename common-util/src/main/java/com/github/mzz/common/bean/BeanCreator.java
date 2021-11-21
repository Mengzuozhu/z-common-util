package com.github.mzz.common.bean;

import org.objenesis.ObjenesisStd;
import org.objenesis.instantiator.ObjectInstantiator;

/**
 * The type Bean creator.
 *
 * @author mengzz
 */
public class BeanCreator {
    private final static ObjenesisStd OBJENESIS_STD = new ObjenesisStd();

    /**
     * New instance.
     *
     * @param <T>   the type parameter
     * @param clazz the clazz
     * @return the t
     */
    public static <T> T newInstance(Class<T> clazz) {
        return OBJENESIS_STD.newInstance(clazz);
    }

    /**
     * Gets instantiator of.
     *
     * @param <T>   the type parameter
     * @param clazz the clazz
     * @return the instantiator of
     */
    public static <T> ObjectInstantiator<T> getInstantiatorOf(Class<T> clazz) {
        return OBJENESIS_STD.getInstantiatorOf(clazz);
    }
}
