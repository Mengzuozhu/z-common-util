package com.github.mzz.common.util;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * List util.
 *
 * @author mengzz
 */
public class ListUtil {

    /**
     * 集合按照'指定集合'的顺序进行排序，忽略不在'指定集合'中的元素
     *
     * @param <E>        the type parameter
     * @param <T>        the type parameter
     * @param collection 待排序集合
     * @param sortRef    指定集合
     * @param keyMapper  the key mapper
     * @return the list
     */
    public static <E, T> List<T> sortByRefCollection(Collection<T> collection, Collection<E> sortRef,
                                                     Function<T, E> keyMapper) {
        if (CollectionUtils.isEmpty(collection) || CollectionUtils.isEmpty(sortRef)) {
            return new ArrayList<>();
        }
        Map<E, T> map = StreamUtil.toMap(collection, keyMapper, v -> v);
        return sortRef.stream()
                .map(map::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public static <E, T> List<T> sortRepeatByRefCollection(Collection<T> collection, Collection<E> sortRef,
                                                           Function<T, E> keyMapper) {
        if (CollectionUtils.isEmpty(collection) || CollectionUtils.isEmpty(sortRef)) {
            return new ArrayList<>();
        }
        Map<E, List<T>> map = new HashMap<>(collection.size());
        collection.forEach(v -> map.computeIfAbsent(keyMapper.apply(v), k -> new ArrayList<>()).add(v));
        return sortRef.stream()
                .map(map::get)
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    /**
     * 按顺序比较两个列表是否不同
     *
     * @param <T>         the type parameter
     * @param list1       the list 1
     * @param list2       the list 2
     * @param biPredicate the biPredicate
     * @return the boolean
     */
    public static <T> boolean isListDiffInOrder(List<T> list1, List<T> list2, BiPredicate<T, T> biPredicate) {
        if (list1 == list2) {
            return false;
        }
        if (list1 == null || list2 == null || list1.size() != list2.size()) {
            return true;
        }

        for (int i = 0; i < list1.size(); i++) {
            if (biPredicate.test(list1.get(i), list2.get(i))) {
                return true;
            }
        }
        return false;

    }

    /**
     * 获取第一个元素
     *
     * @param <T>  the type parameter
     * @param list the list
     * @return the first or null
     */
    public static <T> T getFirstOrNull(List<T> list) {
        return CollectionUtils.isNotEmpty(list) ? list.get(0) : null;
    }

}
