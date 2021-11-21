package com.github.mzz.common.bean;

import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.beans.BeanMap;
import org.apache.commons.collections4.CollectionUtils;
import org.objenesis.instantiator.ObjectInstantiator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The type Bean copy util.
 *
 * @author mengzz
 */
public final class BeanCopyUtil {
    private final static Map<Class<?>, Map<Class<?>, BeanCopier>> BEAN_COPIER_CACHE = new ConcurrentHashMap<>();

    /**
     * Instantiates a new Bean copy util.
     */
    private BeanCopyUtil() {
    }

    /**
     * 拷贝属性
     * 注：不支持包装类型与基本类型互相拷贝
     *
     * @param <T>    the type parameter
     * @param source the source
     * @param target the target
     * @return the t
     */
    public static <T> T copy(Object source, Class<T> target) {
        return copy(source, BeanCreator.newInstance(target));
    }

    /**
     * 拷贝属性
     * 注：不支持包装类型与基本类型互相拷贝
     *
     * @param <T>    the type parameter
     * @param source the source
     * @param target the target
     * @return the t
     */
    public static <T> T copy(Object source, T target) {
        BeanCopier beanCopier = getCopierCache(source.getClass(), target.getClass());
        beanCopier.copy(source, target, null);
        return target;
    }

    /**
     * 拷贝属性
     * 注：不支持包装类型与基本类型互相拷贝
     *
     * @param <T>     the type parameter
     * @param sources the sources
     * @param target  the target
     * @return the list
     */
    public static <T> List<T> copyList(List<?> sources, Class<T> target) {
        if (CollectionUtils.isEmpty(sources)) {
            return new ArrayList<>();
        }

        List<T> result = new ArrayList<>(sources.size());
        ObjectInstantiator<T> instantiator = BeanCreator.getInstantiatorOf(target);
        for (Object source : sources) {
            T newInstance = instantiator.newInstance();
            BeanCopier beanCopier = getCopierCache(source.getClass(), target);
            beanCopier.copy(source, newInstance, null);
            result.add(newInstance);
        }
        return result;
    }

    /**
     * Map to bean.
     *
     * @param <T>    the type parameter
     * @param source the source
     * @param target the target
     * @return the t
     */
    public static <T> T mapToBean(Map<?, ?> source, Class<T> target) {
        T bean = BeanCreator.newInstance(target);
        BeanMap.create(bean).putAll(source);
        return bean;
    }

    /**
     * Bean to map.
     *
     * @param <T>    the type parameter
     * @param source the source
     * @return the map
     */
    public static <T> Map<String, Object> beanToMap(T source) {
        @SuppressWarnings("unchecked")
        Map<String, Object> map = BeanMap.create(source);
        return map;
    }

    /**
     * Gets copier cache.
     *
     * @param <S>    the type parameter
     * @param <T>    the type parameter
     * @param source the source
     * @param target the target
     * @return the copier cache
     */
    private static <S, T> BeanCopier getCopierCache(Class<S> source, Class<T> target) {
        return BEAN_COPIER_CACHE.computeIfAbsent(source, clz -> new ConcurrentHashMap<>(16))
                .computeIfAbsent(target, clz -> BeanCopier.create(source, target, false));
    }
}

