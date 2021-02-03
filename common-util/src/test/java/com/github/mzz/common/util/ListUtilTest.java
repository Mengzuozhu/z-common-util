package com.github.mzz.common.util;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

/**
 * The type List util test.
 *
 * @author mengzz
 */
class ListUtilTest {

    @Test
    void sortByRefList() {
        List<String> list = Lists.newArrayList("1", "2", "2", "4");
        List<String> ref = Lists.newArrayList("3", "2", "1");
        List<String> sort = ListUtil.sortByRefCollection(list, ref, v -> v);
        Assertions.assertEquals(Lists.newArrayList("2", "1"), sort);
    }

    @Test
    void sortRepeatByRefCollection() {
        List<String> list = Lists.newArrayList("1", "2", "2", "4");
        List<String> ref = Lists.newArrayList("3", "2", "1");
        List<String> sortRepeat = ListUtil.sortRepeatByRefCollection(list, ref, v -> v);
        Assertions.assertEquals(Lists.newArrayList("2", "2", "1"), sortRepeat);
    }

    @Test
    void isListDiffInOrder() {
        List<String> list1 = Lists.newArrayList("1", "2", "2");
        List<String> list2 = Lists.newArrayList("3", "2", "1");
        boolean diff = ListUtil.isListDiffInOrder(list1, list2, (a, b) -> !Objects.equals(a, b));
        Assertions.assertTrue(diff);
    }

    @Test
    void isListSameInOrder() {
        List<String> list2 = Lists.newArrayList("3", "2", "1");
        List<String> list3 = Lists.newArrayList("3", "2", "1");
        boolean same = !ListUtil.isListDiffInOrder(list2, list3, (a, b) -> !Objects.equals(a, b));
        Assertions.assertTrue(same);
    }
}
