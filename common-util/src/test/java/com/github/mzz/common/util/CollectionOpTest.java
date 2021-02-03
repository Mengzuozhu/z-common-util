package com.github.mzz.common.util;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Collection op test.
 *
 * @author mengzz
 */
public class CollectionOpTest {

    @Test
    public void intersection() {
        List<Integer> collection1 = Lists.newArrayList(1, 2, 4);
        List<Integer> collection2 = Lists.newArrayList(1, 3, 4);
        List<Integer> intersection = CollectionOp.intersection(collection1, collection2, Collectors.toList());
        Assertions.assertEquals(Lists.newArrayList(1, 4), intersection);
    }
}
