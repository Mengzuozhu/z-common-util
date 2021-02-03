package com.github.mzz.common.util;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * stream工具包装
 *
 * @author mengzz
 */
public class StreamUtil {
    /**
     * To list by map.
     *
     * @param <E>        the type parameter
     * @param <R>        the type parameter
     * @param collection the in list
     * @param mapper     the mapper
     * @return the list
     */
    public static <E, R> List<R> toListByMap(Collection<E> collection, Function<? super E, ? extends R> mapper) {
        if (CollectionUtils.isEmpty(collection)) {
            return new ArrayList<>();
        }
        return collection.stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    /**
     * To list by map parallel list.
     *
     * @param <E>        the type parameter
     * @param <R>        the type parameter
     * @param collection the collection
     * @param mapper     the mapper
     * @return the list
     */
    public static <E, R> List<R> toListByMapParallel(Collection<E> collection,
                                                     Function<? super E, ? extends R> mapper) {
        if (CollectionUtils.isEmpty(collection)) {
            return new ArrayList<>();
        }
        return collection.parallelStream()
                .filter(Objects::nonNull)
                .map(mapper)
                .collect(Collectors.toList());
    }

    /**
     * To list by filter.
     *
     * @param <E>        the type parameter
     * @param collection the collection
     * @param predicate  the predicate
     * @return the list
     */
    public static <E> List<E> toListByFilter(Collection<E> collection, Predicate<? super E> predicate) {
        if (CollectionUtils.isEmpty(collection)) {
            return new ArrayList<>();
        }
        return collection.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    /**
     * To set by map.
     *
     * @param <E>        the type parameter
     * @param <R>        the type parameter
     * @param collection the collection
     * @param mapper     the mapper
     * @return the set
     */
    public static <E, R> Set<R> toSetByMap(Collection<E> collection, Function<? super E, ? extends R> mapper) {
        if (CollectionUtils.isEmpty(collection)) {
            return new HashSet<>();
        }
        return collection.stream()
                .map(mapper)
                .collect(Collectors.toSet());
    }

    /**
     * To set by filter.
     *
     * @param <E>        the type parameter
     * @param collection the collection
     * @param predicate  the predicate
     * @return the set
     */
    public static <E> Set<E> toSetByFilter(Collection<E> collection, Predicate<? super E> predicate) {
        if (CollectionUtils.isEmpty(collection)) {
            return new HashSet<>();
        }
        return collection.stream()
                .filter(predicate)
                .collect(Collectors.toSet());
    }

    /**
     * To map.
     * 注：若存在重复的key，则新值会覆盖旧值
     *
     * @param <T>         the type parameter
     * @param <K>         the type parameter
     * @param <U>         the type parameter
     * @param collection  the collection
     * @param keyMapper   the key mapper
     * @param valueMapper the value mapper
     * @return the map
     */
    public static <T, K, U> Map<K, U> toMap(Collection<T> collection, Function<? super T, ? extends K> keyMapper,
                                            Function<? super T, ? extends U> valueMapper) {
        if (CollectionUtils.isEmpty(collection)) {
            return new HashMap<>(1);
        }
        Map<K, U> result = new HashMap<>(collection.size());
        for (T t : collection) {
            result.put(keyMapper.apply(t), valueMapper.apply(t));
        }
        return result;
    }

    /**
     * To map by stream.
     *
     * @param <T>         the type parameter
     * @param <K>         the type parameter
     * @param <U>         the type parameter
     * @param collection  the collection
     * @param keyMapper   the key mapper
     * @param valueMapper the value mapper
     * @return the map
     */
    public static <T, K, U> Map<K, U> toMapByStream(Collection<T> collection,
                                                    Function<? super T, ? extends K> keyMapper,
                                                    Function<? super T, ? extends U> valueMapper) {
        if (CollectionUtils.isEmpty(collection)) {
            return new HashMap<>(1);
        }
        return collection.stream()
                .collect(HashMap::new,
                        (state, t) -> state.put(keyMapper.apply(t), valueMapper.apply(t)), HashMap::putAll);
    }

    /**
     * To map parallel
     *
     * @param <T>         the type parameter
     * @param <K>         the type parameter
     * @param <U>         the type parameter
     * @param collection  the collection
     * @param keyMapper   the key mapper
     * @param valueMapper the value mapper
     * @return the map
     */
    public static <T, K, U> Map<K, U> toMapParallel(Collection<T> collection,
                                                    Function<? super T, ? extends K> keyMapper,
                                                    Function<? super T, ? extends U> valueMapper) {
        if (CollectionUtils.isEmpty(collection)) {
            return new HashMap<>(1);
        }
        return collection.parallelStream()
                .collect(Collectors.toConcurrentMap(keyMapper, valueMapper, (oldValue, newValue) -> newValue));
    }

    /**
     * To map by filter map.
     *
     * @param <T>         the type parameter
     * @param <K>         the type parameter
     * @param <U>         the type parameter
     * @param collection  the collection
     * @param predicate   the predicate
     * @param keyMapper   the key mapper
     * @param valueMapper the value mapper
     * @return the map
     */
    public static <T, K, U> Map<K, U> toMapByFilter(Collection<T> collection, Predicate<? super T> predicate,
                                                    Function<? super T, ? extends K> keyMapper,
                                                    Function<? super T, ? extends U> valueMapper) {
        if (CollectionUtils.isEmpty(collection)) {
            return new HashMap<>(1);
        }
        return collection.stream()
                .filter(predicate)
                .collect(Collectors.toMap(keyMapper, valueMapper, (oldValue, newValue) -> newValue));
    }

    /**
     * 查找第一个匹配的元素。若不存在，则返回null
     *
     * @param <E>        the type parameter
     * @param collection the collection
     * @param predicate  the predicate
     * @return the e
     */
    public static <E> E findFirstOrNull(Collection<E> collection, Predicate<? super E> predicate) {
        if (CollectionUtils.isEmpty(collection)) {
            return null;
        }
        return collection.stream()
                .filter(predicate)
                .findFirst()
                .orElse(null);
    }

    /**
     * For each skill null.
     *
     * @param <E>        the type parameter
     * @param collection the collection
     * @param action     the action
     */
    public static <E> void forEachSkipNull(Collection<E> collection, Consumer<? super E> action) {
        if (CollectionUtils.isEmpty(collection) || action == null) {
            return;
        }
        collection.forEach(t -> {
            if (t != null) {
                action.accept(t);
            }
        });
    }

    /**
     * For each skill null by mapper.
     *
     * @param <E>        the type parameter
     * @param <R>        the type parameter
     * @param collection the collection
     * @param mapper     the mapper
     * @param action     the action
     */
    public static <E, R> void forEachSkipNullByMapper(Collection<E> collection,
                                                      Function<? super E, ? extends R> mapper,
                                                      Consumer<? super R> action) {
        if (CollectionUtils.isEmpty(collection) || action == null) {
            return;
        }
        collection.stream()
                .map(mapper)
                .forEach(t -> {
                    if (t != null) {
                        action.accept(t);
                    }
                });
    }


}
