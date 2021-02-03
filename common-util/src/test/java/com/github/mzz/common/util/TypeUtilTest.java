package com.github.mzz.common.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mengzz
 **/
class TypeUtilTest {
    private Object str = "1";

    @Test
    void convertAs() {
        Object obj = new ArrayList<>();
        Assertions.assertNotNull(TypeUtil.convertAs(obj, List.class));
    }

    @Test
    void consumeForType() {
        TypeUtil.consumeForType(str, String.class, s -> Assertions.assertEquals(str, s));
    }

    @Test
    void applyForType() {
        String res = TypeUtil.applyForType(str, String.class, s -> s + "2");
        Assertions.assertEquals("12", res);
    }

    @Test
    void isAnyNull() {
        Assertions.assertTrue(TypeUtil.isAnyNull(null, "test"));
        Assertions.assertFalse(TypeUtil.isAnyNull("test"));

    }
}
