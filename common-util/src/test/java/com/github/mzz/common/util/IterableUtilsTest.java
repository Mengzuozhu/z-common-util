package com.github.mzz.common.util;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author mengzz
 **/
class IterableUtilsTest {

    @Test
    void getAnyNotNull() {
        Assertions.assertEquals("1", IterableUtils.getAnyNotNull(Lists.newArrayList(null, "1")));
        Assertions.assertNull(IterableUtils.getAnyNotNull(Lists.newArrayList(null, null)));
    }
}
