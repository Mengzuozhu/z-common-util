package com.github.mzz.common.util;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * The type Type util.
 *
 * @author mengzz
 */
public class TypeUtil {
    /**
     * 转换类型，若为null或转换失败，则返回null
     *
     * @param <T> the type parameter
     * @param obj the obj
     * @param cls the cls
     * @return the t
     */
    @Nullable
    public static <T> T convertAs(Object obj, Class<T> cls) {
        Objects.requireNonNull(cls);
        return cls.isInstance(obj) ? cls.cast(obj) : null;
    }

    /**
     * Consume for type.
     *
     * @param <T>      the type parameter
     * @param obj      the obj
     * @param cls      the cls
     * @param consumer the consumer
     */
    public static <T> void consumeForType(Object obj, Class<T> cls, Consumer<T> consumer) {
        if (cls.isInstance(obj)) {
            consumer.accept(cls.cast(obj));
        }
    }

    /**
     * Apply for type.
     *
     * @param <T>  the type parameter
     * @param <R>  the type parameter
     * @param obj  the obj
     * @param cls  the cls
     * @param func the func
     * @return the r
     */
    @Nullable
    public static <T, R> R applyForType(Object obj, Class<T> cls, Function<T, R> func) {
        if (cls.isInstance(obj)) {
            return func.apply(cls.cast(obj));
        }
        return null;
    }

    /**
     * 是否任意一个参数为空
     *
     * @param objects the objects
     * @return the boolean
     */
    public static boolean isAnyNull(Object... objects) {
        if (objects == null) {
            return true;
        }
        for (Object obj : objects) {
            if (obj == null) {
                return true;
            }
        }
        return false;
    }
}
